package files;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

public class ManageCsv {
	CSVReader csvReader;
	List<String[]> fileRows;

	public int readFile(String filePath) throws IOException, CsvException {
		FileReader userFile = new FileReader(filePath);
		this.csvReader = new CSVReaderBuilder(userFile).withSkipLines(1).build();

		this.fileRows = this.csvReader.readAll();
		return this.fileRows.size();
	}

	public String[] getRowData(int rowLine) {
		return this.fileRows.get(rowLine);
	}

	public void closeReadFile() throws IOException {
		this.csvReader.close();
	}

	public void writeToCsv(String filePathResult, List<Map<String, String>> list) throws Exception {
		String[] rowDataArr = new String[6];

		File file = new File(filePathResult);
		FileWriter uploadFile = new FileWriter(file);

		CSVWriter writer = new CSVWriter(uploadFile);

		String[] header = { "City", "Check-in", "Check-out", "Hotel name", "Hotel review", "Booking url" };
		writer.writeNext(header);

		for (Map<String, String> map : list) {
			for (Map.Entry<String, String> m : map.entrySet()) {
				switch (m.getKey()) {
				case "City":
					rowDataArr[0] = m.getValue();
					break;
				case "Check-in":
					rowDataArr[1] = m.getValue();
					break;
				case "Check-out":
					rowDataArr[2] = m.getValue();
					break;
				case "Hotel name":
					rowDataArr[3] = m.getValue();
					break;
				case "Hotel review":
					rowDataArr[4] = m.getValue();
					break;
				case "Booking url":
					rowDataArr[5] = m.getValue();
					break;
				default:
					throw new Exception("The key " + m.getKey() + " has not been found!");
				}
			}
			writer.writeNext(rowDataArr);
		}

		writer.close();
	}
}
