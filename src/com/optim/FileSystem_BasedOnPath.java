package com.optim;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileSystem_BasedOnPath {

	// Method to calculate total file size in a given directory
	public static int calculateSize(Map<String, Object> fileSystem, String path) {
		// Split the input path into parts (based on dot notation)
		String[] directories = path.split("\\.");

		// Start with the root directory
		Map<String, Object> current = fileSystem;

		// Traverse through the directory structure
		for (String dir : directories) {
			if (current.containsKey(dir)) {
				Object next = current.get(dir);

				// If it's a subdirectory, continue traversal
				if (next instanceof Map) {
					current = (Map<String, Object>) next;
				} else {
					// If it's not a directory, return the size of the file
					return (Integer) next;
				}
			} else {
				// If the directory does not exist, return -1 or a message
				System.out.println("Directory not found.");
				return -1;
			}
		}

		// Calculate the total size of all files in the directory
		return getTotalSize(current);
	}

	// Helper method to calculate total size of all files in a given directory
	public static int getTotalSize(Map<String, Object> directory) {
		int totalSize = 0;

		for (Object value : directory.values()) {
			if (value instanceof Integer) {
				totalSize += (Integer) value; // File size
			} else if (value instanceof Map) {
				totalSize += getTotalSize((Map<String, Object>) value); // Recursive call for subdirectories
			}
		}

		return totalSize;
	}

	public static void main(String[] args) {
		// File system based on the provided nested dictionary structure
		Map<String, Object> fileSystem = new HashMap<>();

		// Constructing the file system (same as before)
		Map<String, Object> root = new HashMap<>();
		Map<String, Object> dir1 = new HashMap<>();
		Map<String, Object> dir2 = new HashMap<>();
		Map<String, Object> dir3 = new HashMap<>();
		Map<String, Object> subdir1 = new HashMap<>();
		Map<String, Object> subdir2 = new HashMap<>();
		Map<String, Object> subdir3 = new HashMap<>();
		Map<String, Object> subdir4 = new HashMap<>();
		Map<String, Object> subdir5 = new HashMap<>();
		Map<String, Object> subsubdir1 = new HashMap<>();
		Map<String, Object> subsubdir2 = new HashMap<>();
		Map<String, Object> subsubdir3 = new HashMap<>();
		Map<String, Object> subsubdir4 = new HashMap<>();
		Map<String, Object> subsubdir5 = new HashMap<>();
		Map<String, Object> subsubsubdir1 = new HashMap<>();
		Map<String, Object> subsubsubdir2 = new HashMap<>();
		Map<String, Object> subsubsubdir3 = new HashMap<>();
		Map<String, Object> subsubsubdir4 = new HashMap<>();
		Map<String, Object> subsubdir6 = new HashMap<>(); // Correctly initializing subsubdir6

		// Constructing the file system
		subsubsubdir1.put("file5.txt", 500);
		subsubsubdir1.put("emptydir1", new HashMap<>());
		subsubdir1.put("file3.txt", 50);
		subsubdir1.put("file4.txt", 150);
		subsubdir1.put("subsubsubdir1", subsubsubdir1);
		subdir1.put("file1.txt", 100);
		subdir1.put("file2.txt", 200);
		subdir1.put("subsubdir1", subsubdir1);

		subsubsubdir2.put("file8.txt", 800);
		subsubsubdir2.put("file9.txt", 900);
		subsubdir2.put("file7.txt", 700);
		subsubdir2.put("subsubsubdir2", subsubsubdir2);
		subdir2.put("file6.txt", 300);
		subdir2.put("subsubdir2", subsubdir2);
		subdir2.put("emptydir2", new HashMap<>());

		dir1.put("subdir1", subdir1);
		dir1.put("subdir2", subdir2);
		dir1.put("file10.txt", 1000);

		subsubdir3.put("file11.txt", 400);
		subsubdir3.put("file12.txt", 500);
		subdir3.put("subsubdir3", subsubdir3);
		subdir3.put("subsubdir4", new HashMap<>());
		subdir4.put("file13.txt", 600);

		subsubsubdir3.put("emptydir4", new HashMap<>());
		subsubsubdir3.put("file16.txt", 900);
		subsubsubdir3.put("file17.txt", 1000);
		subsubdir5.put("file14.txt", 700);
		subsubdir5.put("file15.txt", 800);
		subsubdir5.put("subsubsubdir3", subsubsubdir3);
		subdir4.put("subsubdir5", subsubdir5);

		dir2.put("subdir3", subdir3);
		dir2.put("subdir4", subdir4);
		dir2.put("emptydir5", new HashMap<>());

		subsubsubdir4.put("file20.txt", 1300);
		subsubsubdir4.put("emptydir6", new HashMap<>());
		subsubdir6.put("file19.txt", 1200); // Assign files to subsubdir6
		subsubdir6.put("subsubsubdir4", subsubsubdir4); // Add subsubsubdir4 to subsubdir6
		subdir5.put("subsubdir6", subsubdir6); // Correctly assign subsubdir6 to subdir5
		dir3.put("file18.txt", 1100);
		dir3.put("subdir5", subdir5);

		root.put("dir1", dir1);
		root.put("dir2", dir2);
		root.put("dir3", dir3);
		root.put("emptydir7", new HashMap<>());
		root.put("file21.txt", 1400);

		fileSystem.put("root", root);

		// Create a scanner object to get user input for the path
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the path (e.g., root.dir1.subdir1): ");
		String path = scanner.nextLine();

		// Calculate and display the total size for the given path
		int totalSize = calculateSize(fileSystem, path);
		if (totalSize != -1) {
			System.out.println("Total size: " + totalSize);
		}
	}
}
