package com.ap.portfolio.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/cv")
public class CvDownload {
    @GetMapping("/download")
    public void downloadCv(HttpServletResponse response) throws IOException {
            File cv = new File("src/main/java/com/ap/portfolio/utilities/cv/SantillanLautaroNahuelJrJavaDev.pdf");

        response.setContentType("application/octect-stream");
        String headerKey = "Content-Disposition";
        String headerValue= "attachment; filename=" + cv.getName();

        response.setHeader(headerKey, headerValue);

        ServletOutputStream outputStream = response.getOutputStream();

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(cv));

        byte[] buffer = new byte[8192];
        int bytesRead =-1;

        while ((bytesRead = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, bytesRead);

            inputStream.close();
            outputStream.close();
        }
    }


}

