import java.io.IOException;


public class FileData {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String file_name = "graph.txt";
		
		try{
			Graphing file = new Graphing(file_name);
			String[] aryLines = file.OpenFile();
			
			int i;
			for (i=0; i<aryLines.length; i++){
				System.out.println(aryLines[i]);
			}
		}
		catch (IOException e){
			System.out.println( e.getMessage());
		}

	}

}
