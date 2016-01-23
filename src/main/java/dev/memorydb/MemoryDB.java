package dev.memorydb;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

public class MemoryDB {

	private static Stack<Transaction> transactions;

	private static String executeCommand(String inputLine) {
		String[] parsedLine = inputLine.split(" ");
		Transaction currentTransaction = transactions
				.get(transactions.size() - 1);
		switch (parsedLine[0].toUpperCase()) {
		case "SET": {
			if (parsedLine.length > 2) {
				currentTransaction.set(parsedLine[1], parsedLine[2]);
				return "OK";
			}
			break;
		}
		case "GET": {
			if (parsedLine.length > 1)
				return currentTransaction.get(parsedLine[1]);
			break;
		}
		case "DELETE": {
			if (parsedLine.length > 1) {
				currentTransaction.delete(parsedLine[1]);
				return "OK";
			}
			break;
		}
		case "COUNT": {
			if (parsedLine.length > 1)
				return Integer
						.toString(currentTransaction.count(parsedLine[1]));
			break;
		}

		// transactions
		case "BEGIN": {
			transactions.push(new Transaction(transactions.get(transactions
					.size() - 1)));
			return "OK";
		}

		case "COMMIT": {
			if (transactions.size() == 1)
				return "NO TRANSACTIONS";
			while (transactions.size() > 1) {
				currentTransaction = transactions.pop();
				currentTransaction.commit();
			}
			return "COMMIT SUCCESSFUL";
		}

		case "ROLLBACK": {
			if (transactions.size() > 1) {
				transactions.pop().rollback();
				return "ROLLBACK SUCCESSFUL";
			} else {
				return "NO TRANSACTIONS";
			}
		}
		default:
			return "ERROR";
		}
		return "ERROR";
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner finput;
		String input;
		finput = new Scanner(System.in);

		PrintWriter log = new PrintWriter(
				"/Users/gaurav/dev/interview/box/command.log");

		transactions = new Stack<Transaction>();
		transactions.push(new Transaction(null));

		System.out.print(">>> ");

		while (finput.hasNextLine()) {
			input = finput.nextLine();
			if (input.equalsIgnoreCase("END"))
				break;
			String output = executeCommand(input);
			System.out.println(output == null ? "NULL" : output);
			if (output != null && !output.equalsIgnoreCase("ERROR"))
				log.println(input);
			System.out.print(">>> ");
			log.flush();
		}
		finput.close();
		log.close();
	}

}
