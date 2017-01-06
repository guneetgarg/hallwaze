package com.hallwaze.report;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart {

	public static void GeneratePIEChart(LinkedHashMap<String, String> hmap) {

//		LinkedHashMap<String, String> hmap = CalculateTestCount.TestCount();

		DefaultPieDataset dataset = new DefaultPieDataset();

		for (Map.Entry<String, String> m : hmap.entrySet()) {
			if (m.getKey().equalsIgnoreCase("total") || m.getKey().equalsIgnoreCase("passed")
					|| m.getKey().equalsIgnoreCase("failed") || m.getKey().equalsIgnoreCase("skipped"))
				dataset.setValue(m.getKey() + "  " + m.getValue(), Integer.parseInt(m.getValue()));
		}

		JFreeChart chart = ChartFactory.createPieChart("Test Case Status", dataset, true, true, false);

		int width = 380; /* Width of the image */
		int height = 300; /* Height of the image */
		File pieChart = new File("PieChart.jpeg");
		try {
			ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
