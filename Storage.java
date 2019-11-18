import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.lang.*;

//Interface for a SpellChecker tree data structure to implement
public interface Storage {
	public void insert(String value);
	public boolean search(String value);
	default char[] swap(int i, int j, String str)
	{
		char ch[] = str.toCharArray(); 
        char temp = ch[i]; 
        ch[i] = ch[j]; 
        ch[j] = temp; 
        return ch;
	}
	//The method to suggest values is the same for both, so we can make it default on the interface
	default List<String> suggest(String value) {
		//It starts initializng the list to store all values
		List<String> suggestions = new ArrayList<String>();
		
		//It starts by looping through the word characters, and it changes the input into lowercase letters.
		String newWord;
		char[] vowels = {'a','e','i','o','u','A', 'E', 'I', 'O', 'U'};
		String abc = "abcdefghijklmnopqrstuvwxyzABCDEF'";
		char[] allLeters = abc.toCharArray();
		//These are the main and normal suggestions a spell checker would usually print or show.
		//This adds a letter to the end of the mispelled word 
		for(int j=0; j < allLeters.length; j++)
		{
			String temp = value;
			if(search(temp+=allLeters[j]) && !suggestions.contains(temp))
			{
				suggestions.add(temp);
			}
		}

		//this changes every letter that may prevent the correct spelling of the word
		for(int j=0; j < value.length(); j++)
		{
			for(int k=0; k < allLeters.length; k++)
			{
				String temp2 = value;
				temp2 = value.replace(value.charAt(j), allLeters[k]);
				if(search(temp2) && !suggestions.contains(temp2))
				{
					suggestions.add(temp2);
				}
			}
		}
		//removes the last letter from a mispelled word
		//example: helloo = hello
		String temp3 = "";
		for(int j=0; j < value.length()-1; j++)
		{
			temp3 += value.charAt(j);
		}
		if(search(temp3) && !suggestions.contains(temp3))
		{
			suggestions.add(temp3);
		}
		temp3 = "";
		//removes the first letter from a mispelled word
		for(int j=1; j < value.length(); j++)
		{
			temp3 += value.charAt(j);
		}
		if(search(temp3) && !suggestions.contains(temp3))
		{
			suggestions.add(temp3);
		}
		//this swap letters if they are incorrect order
		//example: neice = niece
		for(int i=0; i < value.length()-1; i++)
		{
			char[] swap1 = swap(i,i+1, value);
			String temp = new String(swap1);
			if(search(temp) && !suggestions.contains(temp))
			{
				suggestions.add(temp);
			}
		}
		//Here, it is going to loop through the mispelled word and, when it gets to a vowel, it will consider the next cases:
		//1. There's another vowel after or before
		//2. The current vowel doesn't go on the word
		//3. The letters right after and before the vowel are different
		for(int i = 0; i < value.length(); i++)
		{
			//First, it checks if the current char is a vowel
			char c = value.charAt(i);
			if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')
			{
				//1. it checks adding vowels before or after
				//Loop to add vowels before
				for(int v = 0; v < vowels.length; v++) {
					newWord = value.substring(0,i) + vowels[v] + value.substring(i);
					if(search(newWord) && !suggestions.contains(newWord)) {
						suggestions.add(newWord);
					}
				}
				for(int v = 0; v < vowels.length; v++) {
					newWord = value.substring(0,i+1) + vowels[v] + value.substring(i+1);
					if(search(newWord) && !suggestions.contains(newWord)) {
						suggestions.add(newWord);
					}
				}
				//2. it removes the current vowel
				newWord = value.substring(0,i) + value.substring(i+1);
				if(search(newWord) && !suggestions.contains(newWord)) {
					suggestions.add(newWord);
				}
				//3. It replaces letters that are different before and after
				//Loop to replace the letter before
				for(int l = 0; l < allLeters.length; l++) {
					if(i == 0) {
						newWord = allLeters[l] + value.substring(i);
					} else {
						newWord = value.substring(0,i-1) + allLeters[l] + value.substring(i);
					}
					if(search(newWord) && !suggestions.contains(newWord)) {
						suggestions.add(newWord);
					}
				}
				//Loop to replace the letter after
				for(int l = 0; l < allLeters.length; l++) {
					if(i == value.length()-1) {
						newWord = value.substring(0,i+1) + allLeters[l];
					} else {
						newWord = value.substring(0,i+1) + allLeters[l] + value.substring(i+2);
					}
					if(search(newWord) && !suggestions.contains(newWord)) {
						suggestions.add(newWord);
					}
				}
			}
			
			//These are the two special cases that I have.
			//1. Any time we get to an s, t, f, l, m or b, we try to see if these letters need to be repeated, 
			//this is because they're the most repeated letters in english 
			//2. If there's a repeated letter, we try to consider it not repeated

			//Special case 1: If the current word has s, t, f, l, m or b, it doubles it
			if(c == 's' || c == 't' || c == 'f' || c == 'l' || c == 'm' || c == 'b')
			{
				newWord = value.substring(0,i) + c + value.substring(i);
				if(search(newWord) && !suggestions.contains(newWord)) {
					suggestions.add(newWord);
				}
			}
			//Special case 2: If there's a repeated letter, it considers it as not repeated
			if(i != 0 && value.charAt(i-1) == c) {
				newWord = value.substring(0,i) + value.substring(i+1);
				if(search(newWord) && !suggestions.contains(newWord)) {
					suggestions.add(newWord);
				}
			}

		}
		return suggestions;
	}

}