
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;




public class GoL {
	
	public static List<List<Integer>> list = new ArrayList<List<Integer>>();
	public static Stack<Struct> stack = new Stack<Struct>();
	
	
	public static void main(String[] args) throws IOException 
	{
		
		String n = new String(); 
		//n = "1";
		String location = new String();
		//location = "src/array.txt";
		String output = new String();
		//output = "output";
		for(int k = 0 ; k < args.length; k++)
		{
			
			if (args[k].equals("-i"))
			{
				
				try {
					   location = args[k+1];
					}
					catch(ArrayIndexOutOfBoundsException exception) {
					    location = "array.txt"; 
					}
			}
		
			
			if (args[k].equals("-n"))
			{				
				try {
					   n = args[k+1];
					}
					catch(ArrayIndexOutOfBoundsException exception) {
					    n = "0"; 
					}
			}
			
			if (args[k].equals("-o"))
			{
				try {
				   output = args[k+1];
				}
				catch(ArrayIndexOutOfBoundsException exception) {
				    output = "output"; 
				}
				
			}
		
			
		}
		
		
		 List<String> lines=Files.readAllLines(Paths.get(location), Charset.forName("UTF-8")); 
		 read_input(lines);
				
		
		long startTime = System.nanoTime();
		for(int k = 0 ; k < Integer.parseInt(n) ; k++)
		{
			bounds();				
			step();
			unbound();
		}		
		long endTime = System.nanoTime();
		print_grid();
		System.out.println((endTime-startTime)/1000000 +" miliseconds");
		
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(output+".txt"));
		for (List<Integer> elements:list)
		{
			writer.write(elements.toString().replace(",","").replace("[","").replace("]",""));
			writer.newLine();
		}
		writer.close();

	}
	

	public static void read_input(List<String> lines)
	{		
	    for ( int i = 0 ; i < lines.size() ; i++)
	    {
	    	 list.add(new ArrayList<Integer>());
	    	 
			  for (int j = 0 ; j < lines.get(i).length() ; j++)
			  {
				  int s = Character.getNumericValue(lines.get(i).charAt(j));
				  list.get(i).add(s);
			  }
	    }
	   
	 
	}
	
	public static void print_grid() throws IOException
	{
		
		for(List<Integer> temp_line:list)
		{		
			System.out.println(temp_line.toString().replace(",","").replace("[","").replace("]",""));			
		}
	}
	
	public static void bounds()

	{
		
		// check lower bound
		for (int element:list.get(list.size()-1))
		{
			if (element==1)
			{    
				list.add(new ArrayList<Integer>(Collections.nCopies(list.get(0).size(), 0)));
				list.add(new ArrayList<Integer>(Collections.nCopies(list.get(0).size(), 0)));
				break;				
			}			
		}
		// check right bound
		for (int i = 0; i < list.size() ; i++)
		{
			if (list.get(i).get(list.get(i).size()-1)==1)
				{
				     for(List<Integer> temp_list:list)
				     {
				    	 temp_list.add(0);
				    	 temp_list.add(0);
				     }
				}	
		}
		
		//Check left bound
		for (int i = 0; i < list.size() ; i++)
		{
			if (list.get(i).get(0)==1)
			{
				for(List<Integer> temp_list:list)
				{
					temp_list.add(0,0);
					temp_list.add(0,0);
				}
			}
		}
		
		//Check upper bound
		for(int element:list.get(0))
		{
			if (element == 1)
			{
				list.add(0,new ArrayList<Integer>(Collections.nCopies(list.get(0).size(), 0)));	
				list.add(0,new ArrayList<Integer>(Collections.nCopies(list.get(0).size(), 0)));	
				break;		
			}
		}
		
	
	}
		
	public static void unbound()
	{
		
				
		//check upper bound
			for (int i = 0; i < list.size() ; i++)
			{
				int sum1 = sum(list.get(i));
				if(sum1==0)
				{
					list.remove(list.get(i));
					i = i-1;	
				}
				else break; 
			}
		//check lower bound			
			for (int i = list.size()-1; i >=0 ; i--)
			{
				int sum1 = sum(list.get(i));
				if(sum1 == 0)
				{
					list.remove(list.size()-1);
					
				}
				else break;
			}
			
			// check left bound
			int sum = 0;
			while(sum==0)
			{
				for ( int i =0 ; i < list.size();  i++)
				{
					sum = sum + list.get(i).get(0);
					
				}
				if(sum==0)
				{
					for ( int i =0 ; i < list.size();  i++)
					{
						list.get(i).remove(0);
					}
				}
				else break;
			}
			
			//check right bound
			sum = 0; 
			while(sum==0)
			{
				for (int i=0; i < list.size(); i++)
				{
					sum = sum + list.get(i).get(list.get(i).size()-1);
				}
				if (sum ==0)
				{
					for (int i =0; i < list.size(); i++)
					{
						list.get(i).remove(list.get(i).size()-1);
					}
				}
				else break; 
			}
		
	}
	
	public static void step()
	{

		for ( int i = 1; i < list.size()-1; i++)
		{
			for (int j = 1 ; j < list.get(i).size()-1; j++)
			{
				
				int sum = list.get(i-1).get(j-1)+list.get(i-1).get(j)+list.get(i-1).get(j+1)+list.get(i).get(j-1)+list.get(i).get(j+1)+list.get(i+1).get(j-1)+list.get(i+1).get(j)+list.get(i+1).get(j+1);
			
				if (list.get(i).get(j)==1)
				{
					if (sum<2 | sum>3)
					{
						Struct str = new Struct();
						str.row = i ;
						str.col = j ;
						str.val = 0; 
						//str.print();
						stack.push(str);
						//list.get(i).set(j,0); 
					}		
				}
				else
				{
					if (sum==3)
					{
						Struct str = new Struct();
						str.row = i;
						str.col = j;
						str.val = 1; 
						//str.print();
						stack.push(str);
						//list.get(i).set(j,1);
					}
				}
			}
		}
		
		
		try {
			while (stack!=null)
			{
				Struct str = new Struct();				
				str = stack.pop();
				list.get(str.row).set(str.col,str.val);				
				
			}
			
			} catch (EmptyStackException e) { 
		
			} 
		
		
		
	}

	public static int sum (List<Integer> list) 
	{
	    int sum = 0;
	    for (int i: list) {
	        sum += i;
	    }
	    return sum;
	}

}
