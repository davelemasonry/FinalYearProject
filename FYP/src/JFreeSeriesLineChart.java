import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Stroke;
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


/**
 * A simple demonstration application showing how to create a line chart using data from a
 * {@link CategoryDataset}.
 */
public class JFreeSeriesLineChart extends ApplicationFrame {

    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    
    
    public JFreeSeriesLineChart(final String title) {
        super(title);
        
        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        
        final ChartPanel chartPanel = new ChartPanel(chart);
        this.add(chartPanel, BorderLayout.CENTER);
        
        //Adds a label at bottom of the chart. You can use it to show some useful information
       //JPanel customPanel = new JPanel();
       //JLabel lbl = new JLabel("<html><h3>Note : You can add any important note here.</h3></html>");
        //customPanel.add(lbl);
        //this.add(customPanel, BorderLayout.SOUTH);
        
        
        
    }
    /**
     * Returns a sample dataset.
     * 
     * @return The dataset.
     */
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
				dataset.addValue(value2,"Stuff",number);
				System.out.println(aryLines[i]);
			}
		}
		catch (IOException e){
			System.out.println( e.getMessage());
		}
        
        // create the dataset...
        
        //dataset.addValue(5898, "Series1", "Category1");
        //dataset.addValue(3975, "Series1", "Category2");

        
        return dataset;
        
    }
     /**
     * Creates a sample chart.
     * 
     * @param dataset  a dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(final CategoryDataset dataset) {
        
       final JFreeChart chart = ChartFactory.createLineChart(
            "Time Taken",         // chart title
            "Category",                   // domain(x-axis) axis label
            "Value",                      // range(y-axis) axis label
            dataset,                      // data
            PlotOrientation.VERTICAL,     // orientation
            true,                         // include legend
            true,                         // tooltips
            false                         // urls
        );
        
        
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        //final StandardLegend legend = (StandardLegend) chart.getLegend();
        //legend.setDisplaySeriesShapes(true);
        //legend.setShapeScaleX(1.5);
        //legend.setShapeScaleY(1.5);
        //legend.setDisplaySeriesLines(true);
        
        
        
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

        
        //CUSTOMIZE RANGE AXIS
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        //rangeAxis.setVerticalTickLabels(true);
        //rangeAxis.setAutoTickUnitSelection(rootPaneCheckingEnabled);
        
        //CUSTOMIZE THE RENDERER
        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        // renderer.setDrawShapes(true);
        
        
        //You can also set series line colors for each line
        /*renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesPaint(2, Color.YELLOW);
        renderer.setSeriesPaint(3, Color.GREEN);
        renderer.setSeriesPaint(4, Color.ORANGE);
        renderer.setSeriesPaint(5, Color.white);*/
        
        
        
        //Working Code to show values along with dots
        /*NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        CategoryItemLabelGenerator generator =
            new StandardCategoryItemLabelGenerator(
                StandardCategoryItemLabelGenerator.DEFAULT_LABEL_FORMAT_STRING,format);
                
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);
        */
        
        //set dots or you can say shapes at a point
        renderer.setBaseShapesFilled(true);
        renderer.setBaseShapesVisible(true);
        
        //DISPLAY LINES TYPE
        
        //It would show solid lines
        Stroke stroke = new BasicStroke(
            3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
        renderer.setBaseOutlineStroke(stroke);
        
        //You can also customize your lines like dotted one, dash-ed one etc.
        /* renderer.setSeriesStroke(
            0, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {10.0f, 6.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            1, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {6.0f, 6.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            2, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {2.0f, 6.0f}, 0.0f
            )
        );*/
        
        
        return chart;
    }
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        final JFreeSeriesLineChart demo = new JFreeSeriesLineChart("Series-Line Chart Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}