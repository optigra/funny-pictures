package com.optigra.funnypictures.service.repository;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>A class for converting long numbers into base-62 string representation. 
 * <strong>Negative values are not supported.</strong> </p>
 * Example conversions (base-10 -&gt; base-62):
 * <ul>
 * <li>0 -&gt; 0</li>
 * <li>1 -&gt; 1</li>
 * <li>10 -&gt; A</li>
 * <li>35 -&gt; Z</li>
 * <li>36 -&gt; a</li>
 * <li>61 -&gt; z</li>
 * <li>62 -&gt; 10</li>
 * <li>9223372036854775807 -&gt; AzL8n0Y58m7 (<code>Long.MAX_VALUE</code>)</li>
 * </ul>
 * @author odisseus
 *
 */
public class Base62Converter {
	
	// TODO add support for bases smaller than 62	
	/**
	 * The base for the target numeral system.
	 */
	public static final int BASE = 62;
	
	/**
	 * The characters used as digits. Array index corresponds to numeric value.
	 */
	private final char[] elements = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
		'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
		'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
		'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };
	
	private final Comparator<String> base62Comparator;
	
	/**
	 * Returns a comparator that compares strings by their numeric values.
	 * If one of the arguments is a valid base-62 number representation and the other isn't,
	 * the valid one is greater. 
	 * If neither of the arguments is a valid base-62 number representation, 
	 * they are compared with <code>String.compareTo(String)</code> method.
	 * Null values are not supported.
	 * 
	 * @return the comparator
	 */
	public Comparator<String> getBase62Comparator() {
		return base62Comparator;
	}

	/**
	 * Creates a new base-62 converter.
	 */
	public Base62Converter() {
		this.base62Comparator = (final String s1, final String s2) -> {
			return this.compareAsBase62Numbers(s1, s2);
		};
	}
	
	/**
	 * Converts a given number to base 62.
	 * @param toBeConverted the input number
	 * @return the base-62 string representation of the input
	 */
	public String convertTo62Base(final long toBeConverted) {
		if (toBeConverted < 0) {
			throw new IllegalArgumentException("Negative numbers are not supported: " + toBeConverted);
		}

		int digitsCount = 0;
		long power = 1;
		for (; power <= toBeConverted / BASE; power *= BASE) {
			++digitsCount;
		}
		++digitsCount;

		StringBuilder result = new StringBuilder(toBeConverted < 0 ? "-" : "");

		for (long remainder = toBeConverted; digitsCount-- > 0; remainder %= power, power /= BASE) {
			int digitIndex = (int) (remainder / power);
			result.append(elements[Math.abs(digitIndex)]);
		}

		System.out.println();
		System.out.println();
		return result.toString();
	}
    
    /**
     * Converts a given base-62 number representation to a long.
     * @param toBeConverted base-62 number string
     * @return value of the argument as long
     */
	public long convertToLong(final String toBeConverted) {
		final char[] reversedChars = new StringBuilder(toBeConverted).reverse().toString().toCharArray();
		long power = 1L;
		long result = 0;
		for (char digit : reversedChars) {
			long digitValue = Arrays.binarySearch(elements, digit);
			if (digitValue < 0) {
				throw new IllegalArgumentException(String.format("Illegal character: %s", digit));
			}
			result += power * digitValue;
			power *= BASE;
		}
		return result;
	}
    
    /**
     * Returns a base-62 number representation of a next number.
     * The argument must be a valid base-62 number. <strong>Inputs longer than 10 chars are not supported.</strong>
     * The result is always greater by 1 than the argument.
     * @param input the base-62 number string
     * @return the base-62 string representation of the next number
     */
	public String getNextBase62Number(final String input) {

		final int length = input.length();
		if (length > 10) {
			throw new IllegalArgumentException("Input is longer than 10 chars: " + input);
		}

		for (char c : input.toCharArray()) {
			if (Arrays.binarySearch(elements, c) < 0) {
				throw new IllegalArgumentException(String.format("Illegal character: %s", c));
			}
		}

		return convertTo62Base(convertToLong(input) + 1L);
	}
    
    /**
     * Checks whether the argument is a valid base-62 string representation of a number.
     * @param input the input to test
     * @return <code>true</code> if the input is a valid base-62 number, <code>false</code> otherwise
     */
	public boolean isValidBase62Number(final String input) {
		for (char c : input.toCharArray()) {
			if (Arrays.binarySearch(elements, c) < 0) {
				return false;
			}
		}
		return true;
	}
    
	/**
	 * Compare strings as base-62 numbers.
	 * @param s1 first string
	 * @param s2 second string
	 * @return -1 if s1 &lt; s2; 0 if s1 = s2; 1 if s1 &gt; s2
	 */
    private int compareAsBase62Numbers(final String s1, final String s2) {
    	
    	boolean s1IsValid = isValidBase62Number(s1);
    	boolean s2IsValid = isValidBase62Number(s2);
    	
    	if (s1IsValid) {
    		if (s2IsValid) {
    			// Compare numeric values 
    			return Long.compare(convertToLong(s1), convertToLong(s2));
    		} else {
    			// s1 is greater
    			return 1;
    		}    		
    	} else if (s2IsValid) {
    		// s2 is greater
    		return -1;
    	} else {
    		// Compare lexicografically
    		return s1.compareTo(s2);
    	}   	
    	
    }

}
