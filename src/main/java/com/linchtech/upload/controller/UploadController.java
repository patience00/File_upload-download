package com.linchtech.upload.controller;

import com.linchtech.upload.entity.ScoreFile;
import com.linchtech.upload.mapper.ScoreMapper;
import com.linchtech.upload.util.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author: 107
 * @date: 2019/4/10 13:36
 * @description:
 * @Review:
 */
@Controller
@Slf4j
public class UploadController {

    private static final String SCORE_SYSTEM = "ScoreSystem";
    private static final String KEY = "ScoreSystemFiles";
    private static final String URL_PRIFIX = "http://localhost:8090/picture/";

    private final ScoreMapper scoreMapper;

    @Autowired
    public UploadController(ScoreMapper scoreMapper) {
        this.scoreMapper = scoreMapper;
    }


    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult uploadAttachment(@RequestPart("file") MultipartFile file,
                                         HttpServletRequest request) {
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        String format = yyyyMMdd.format(new Date());
        try {
            InputStream inputStream = file.getInputStream();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            System.out.println("后缀名:" + suffix);
            File destDir = new File(SCORE_SYSTEM);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            File datePath = new File(destDir.getPath() + File.separator + format);
            if (!datePath.exists()) {
                datePath.mkdir();
            }
            String newFileName = datePath + File.separator + uuid;
            System.out.println("文件path=" + newFileName);
            System.out.println("新文件名=" + newFileName);
            // base64编码之后有斜线,影响前端访问,替换掉
            String encrypt = AESUtil.Encrypt(newFileName, KEY).replace("/", "-");;
            System.out.println("加密字符串=" + encrypt);
            File newFile = new File(newFileName + suffix);

            ScoreFile scoreFile = ScoreFile.builder()
                    .name(file.getOriginalFilename())
                    .path(newFileName + suffix)
                    .url(URL_PRIFIX + encrypt + suffix)
                    .wid("2")
                    .build();
            OutputStream out = new FileOutputStream(newFile);
            int a;
            while ((a = inputStream.read()) != -1) {
                out.write(a);
            }
            out.close();
            scoreMapper.insert(scoreFile);
            String decrypt = AESUtil.Decrypt(encrypt.replace("-", "/"), KEY);
            System.out.println("解密:" + decrypt);
            return ReturnResult.success("OK", scoreFile);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ReturnResult.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "/picture/{url}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getFile(@PathVariable("url") String url, HttpServletRequest request) throws IOException {

        System.out.println("url:" + url);
        System.out.println("suffix:" + url.substring(url.indexOf(".")));
        String path = AESUtil.Decrypt(url.replace("-", "/"), KEY);
        System.out.println("本地文件路径:" + path);
        File destFile = new File(path + url.substring(url.indexOf(".")));
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.valueOf("image/jpeg");
        if (MediaType.APPLICATION_OCTET_STREAM.equals(mediaType)) {
            headers.setContentDispositionFormData("attachment", new String("$)(XSJ$T[%}34CT`DZP[Y]F.jpg".getBytes("utf-8"), "ISO8859-1"));
        }
        headers.setContentType(mediaType);
        return new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(destFile), headers, HttpStatus.OK);
    }

}
