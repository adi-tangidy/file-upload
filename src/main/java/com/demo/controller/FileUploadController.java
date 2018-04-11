package com.demo.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {
    
    @Autowired
    private ResourceLoader resourceLoader;
    
    @GetMapping("/file")
    public String listUploadedFiles(){
        return "upload";
    }
    
    @PostMapping("/file")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam String name,
            RedirectAttributes redirectAttributes) {
        try {
            file.transferTo(resourceLoader.getResource(String.format("WEB-INF/resources/uploaded/%s", file.getOriginalFilename())).getFile());
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!" + " to " + (resourceLoader.getResource(String.format("WEB-INF/resources/uploaded/%s", file.getOriginalFilename())).getFile()));

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
//            throw new RuntimeException("File saving failed", e);
        }
        

        return "redirect:/";
    } 
}
