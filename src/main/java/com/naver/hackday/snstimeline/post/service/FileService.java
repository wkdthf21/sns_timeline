package com.naver.hackday.snstimeline.post.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.naver.hackday.snstimeline.common.exception.FileUploadDownloadException;
import com.naver.hackday.snstimeline.config.FileUploadConfig;

@Service
public class FileService {

	private final Path fileLocation;
	private static final String ILLEGAL_EXP = "[:\\\\/%*?:|\"<>].";

	public FileService(FileUploadConfig fileUploadProperty){
		this.fileLocation = Paths.get(fileUploadProperty.getUploadDirectory())
			.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileLocation);
		}catch (IOException e){
			throw new FileUploadDownloadException("file-upload", "Directory 생성에 실패했습니다");
		}
	}

	/* 파일 업로드 함수 */
	public String saveFile(MultipartFile file){

		String fileName = file.getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf("."));

		// 파일명 부적합 검사
		if(isIllegalFileName(fileName))
			throw new FileUploadDownloadException("file-name", "파일이름이 잘못되었습니다.");

		// 파일 고유 이름 생성
		UUID uuid = UUID.randomUUID();
		String newFileName = uuid.toString() + extension;
		System.out.println("file name - " + newFileName);

		try{
			Path targetLocation = this.fileLocation.resolve(newFileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return newFileName;
		}catch (Exception e){
			throw new FileUploadDownloadException(fileName + "-upload", "파일 업로드에 실패했습니다.");
		}
	}

	/* 파일 다운로드 함수 */
	public Resource downloadFileAsResource(String fileName){

		// 파일명 부적합 검사
		if(isIllegalFileName(fileName))
			throw new FileUploadDownloadException("file-name", "파일이름이 잘못되었습니다.");

		try{
			Path filePath = this.fileLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());

			if(resource.exists())
				return resource;
			else
				throw new FileUploadDownloadException(fileName + "not exists", "파일 다운로드에 실패했습니다.");

		}catch(MalformedURLException e){
			throw new FileUploadDownloadException(fileName, "파일 다운로드에 실패했습니다.");
		}
	}

	private boolean isIllegalFileName(String fileName){
		if(fileName == null || fileName.trim().length() == 0) return true;
		if(Pattern.compile(ILLEGAL_EXP).matcher(fileName).find()) return true;
		return false;
	}

}
