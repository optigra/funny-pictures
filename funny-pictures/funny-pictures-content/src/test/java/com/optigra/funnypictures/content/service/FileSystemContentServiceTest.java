package com.optigra.funnypictures.content.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.optigra.funnypictures.content.model.Content;
import com.optigra.funnypictures.content.model.MimeType;

public class FileSystemContentServiceTest {

	private String contentRepositoryPath = "./";
	private String fileName = "test-image.png";

	private FileSystemContentService unit = new FileSystemContentService();

	@Before
	public void setUp() throws IOException {
		unit.setContentRepositoryPath(contentRepositoryPath);
		File file = new File(fileName);
		file.deleteOnExit();
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write("Hello world!!!");
		fileWriter.close();
	}

	@Test
	public void testGetContentByPath() throws Exception {
		// Given
		Content expectedContent = new Content();
		expectedContent.setMimeType(MimeType.IMAGE_PNG_PNG);
		expectedContent.setPath(fileName);
		expectedContent.setSize(Long.valueOf(483901333504L));

		// When
		Content actualContent = unit.getContentByPath(fileName);

		// Then
		assertEquals(expectedContent.getPath(), actualContent.getPath());
		assertEquals(expectedContent.getMimeType(), actualContent.getMimeType());
		assertEquals(expectedContent.getSize(), actualContent.getSize());
	}
}
