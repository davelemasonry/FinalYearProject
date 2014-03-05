import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Stroke;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.axis.CategoryLabelPositions;


public class JFreeSeriesLineChart extends ApplicationFrame {


    public JFreeSeriesLineChart(final String title) {
        super(title);
        
        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        
        final ChartPanel chartPanel = new ChartPanel(chart);
        this.add(chartPanel, BorderLayout.CENTER);
        
        
    }

    public void windowClosing(final WindowEvent evt){
    	if(evt.getWindow() == this){
    		dispose();
    	}
    }
    
    private CategoryDataset createDataset() {
    	final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	
    	String file_name = "graph.txt";
    	try{
			Graphing file = new Graphing(file_name);
			String[] aryLines = file.OpenFile();
			
			int i;
			for (i=0; i<aryLines.length; i++){
				String value = aryLines[i];
				double value2 = Double.parseDouble(value);
				String number = String.valueOf(i);
				dataset.addValue(value2,"Mean Times",number);
			}
		}
		catch (IOException e){
			System.out.println( e.getMessage());
		}
        
        return dataset;
        
    }

    private JFreeChart createChart(final CategoryDataset dataset) {
        
       final JFreeChart chart = ChartFactory.createLineChart(
            "Time Taken",         // chart title
            "Category",                   // domain(x-axis) axis label
            "Value",                      // range(y-axis) axis label
            dataset,                      // data
            PlotOrientation.VERTICAL,     // orientation
            true,                        
            true,                        
            false
        );
        
        // set chart background
        chart.setBackgroundPaint(Color.white);
        

        //set plot specifications 
        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        
        //CUSTOMIZE DOMAIN AXIS
        final CategoryAxis domainAxis = (CategoryAxis) plot.getDomainAxis();
        
        //customize domain label position
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();

        //set dots or you can say shapes at a point
        renderer.setBaseShapesFilled(true);
        renderer.setBaseShapesVisible(true);
        
        //DISPLAY LINES TYPE
        Stroke stroke = new BasicStroke(
            3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
        renderer.setBaseOutlineStroke(stroke);
        
        
        return chart;
    }
    
    public static void main(final String[] args) {

        final JFreeSeriesLineChart chart = new JFreeSeriesLineChart("Enterprise System Averages");
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
        File file = new File("graph.txt");
        file.delete();
    }

}