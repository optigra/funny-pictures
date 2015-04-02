package com.optigra.funnypictures.service.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class Base62ConverterTest {
	
	private static final List<Object[]> CONVERSION_PAIRS = Arrays.asList(new Object[][] { 
		{"0", 0L},
		{"1", 1L},
		{"A", 10L},
		{"10", 62L},
		{"11", 63L},
		{"100", 62L * 62L},
		{"102", 62L * 62L + 2L},
		{"1TCKi2jjzdZ", 1234567890987654321L},
		{"9zzzzzzzzzz", 8392993658683402239L},
		{"A0000000000", 8392993658683402240L},
		{"A0000000001", 8392993658683402241L},
		{"AzL8n0Y58m7", Long.MAX_VALUE},
		{"Myroslav", 80932842381181L},
	});
	
	private static final List<String[]> SEQUENCE_PAIRS = Arrays.asList(new String[][] { 
			{"0", "1"},
			{"1", "2"},
			{"A", "B"},
			{"Z", "a"},
			{"z", "10"},
			{"100", "101"},
			{"1TCKi2jjzd", "1TCKi2jjze"},
			{"A000000000", "A000000001"},
			{"zzzzzzzzzz", "10000000000"}, //10 chars input, 11 chars output
			{"Myroslav", "Myroslaw"},
		});

	private Base62Converter unit = new Base62Converter();
	
	@Test
	public void testConvertTo62Base() throws Exception {
		for(Object[] conversionPair : CONVERSION_PAIRS){
			assertEquals(conversionPair[0].toString(), unit.convertTo62Base((Long) conversionPair[1]));
		}
	}
	
	@Test
	public void testConvertToLong() throws Exception {
		for(Object[] conversionPair : CONVERSION_PAIRS){
			assertEquals(unit.convertToLong(conversionPair[0].toString()), ((Long) conversionPair[1]).longValue());
		}
	}
	
	@Test
	public void testGetNextBase62Number() throws Exception {
		for(String[] sequencePair : SEQUENCE_PAIRS){
			String expected = sequencePair[1];
			String actual = unit.getNextBase62Number(sequencePair[0]);
			assertEquals(expected, actual);
		}
	}
	
	@Test
	public void testBase62Comparator() throws Exception {
		Comparator<String> comparator = unit.getBase62Comparator();
		assertTrue(comparator.compare("0", "1") < 0);
		assertTrue(comparator.compare("1", "0") > 0);
		assertTrue(comparator.compare("-", "1") < 0);
		assertTrue(comparator.compare("0", "-") > 0);
		assertTrue(comparator.compare("a", "a") == 0);
		assertTrue(comparator.compare("-_", "a0-") == "-_".compareTo("a0-"));
		
	}


}
