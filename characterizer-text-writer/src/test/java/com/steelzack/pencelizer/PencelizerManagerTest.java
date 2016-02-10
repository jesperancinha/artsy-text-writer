	package com.steelzack.pencelizer;

import static com.steelzack.pencelizer.distributions.PencelizerDistributionType.Linear;

import java.awt.Color;
import java.io.InputStream;
import java.lang.Character.UnicodeBlock;

import org.junit.Test;

/**
 * 
 * @author joao
 *
 */
public class PencelizerManagerTest {

	@Test
		public void testAddPencelizerCharacter() throws Exception {
			// TODO: Make Junit test
		}

	@Test
	public void testPencelizerManager() throws Exception {
		// TODO: Make Junit test
	}

	@Test
	public void testGenerateConvertedImage_CyanBlack() throws Exception {
		InputStream imageFullStream = getClass().getResourceAsStream("pencelizerCyanBlack.png");
		final PencelizerManager manager = new PencelizerManager( //
				Color.BLACK, //
				50, //
				10, //
				Linear, //
				"Arial", // 
				5, //
				UnicodeBlock.LATIN_EXTENDED_A, //
				imageFullStream, //
				"/tmp/testCyanBlue.png" //
		);
		manager.generateConvertedImage();
	}
	
	@Test
	public void testGenerateConvertedImage_RealExample1() throws Exception {
		InputStream imageFullStream = getClass().getResourceAsStream("RealExample1.jpg");
		final PencelizerManager manager = new PencelizerManager( //
				Color.BLACK, //
				100, //
				100, //
				Linear, //
				"Times New Roman", // 
				20, //
				UnicodeBlock.LATIN_EXTENDED_A, //
				imageFullStream, //
				"/tmp/testRealExample1.png" //
				);
		manager.generateConvertedImage();
	}
}