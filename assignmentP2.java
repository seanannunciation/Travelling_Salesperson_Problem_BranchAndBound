import java.io.*;

import java.lang.reflect.Array;
import java.util.*; 


public class assignmentP2 extends ArrayList<String>{

	public static List<String> LineArray = new ArrayList<String>();
	//public static int sizeA = 0;
	public static String opttour;
	public static int minlength=0;
	public static long startT = 0;
	public static long endT=0;
	public static long total=endT-startT;
	static int costarray[][];
	public static PriorityQueue<Node> X;
	public static int minln;
	public static int ncount=0;
	//public static assignmentP2 list=new assignmentP2();
	
	
	public static void main (String args[]) throws IOException
	{
		startT = System.currentTimeMillis();
		
		String filename="";
		filename=(args.length==0)? "input.txt" :args[0];
	
		FileReader fr = new FileReader(filename);
		BufferedReader br =new BufferedReader(fr);
		assignmentP2 list=new assignmentP2();
		String line=null;
		BufferedWriter bw=new BufferedWriter(new FileWriter(new File(args[1])));	
		
		while ((line=br.readLine())!=null)
		{
			list.add(line);
			
		}
		int count=0;
		int i=0;
		
		try {
				while(!list.isEmpty())
			{
					int size=Integer.parseInt(list.get(0));
					
					costarray=new int[size][size];
				
				for(i=1;i<=(size*size);i++)
				{
					String tempst[]=list.get(i).split("\\s+");
					int row=Integer.parseInt(tempst[0]);
					int column=Integer.parseInt(tempst[1]);
					int value=Integer.parseInt(tempst[2]);
					costarray[row][column]=value;
					
					
				}
				 count=count+1;
				
				
				
				list.removeRange(0,i);
				
				String path=travel(costarray);
				endT = System.currentTimeMillis();
				path=path.replaceAll("\\#",",");
				total=endT-startT;
				bw.write(count+" "+size+" "+ncount+" "+minln+" "+path+" "+total+"\n");
				
				
					
			}
			bw.close();
			
						
			}
						
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
	
	}
	
	public static String travel(int ca[][])
	{
		String opttour="";
	
		Comparator<Node>pqcomp=new LowerBoundComparator();
		PriorityQueue<Node>pq=new PriorityQueue<Node>(ca.length,pqcomp);
		
		Node u=new Node();
		u.path="0";
	
		u.bound=bound(u.path,ca);
		
		int minlength=Integer.MAX_VALUE;
		pq.add(u);
				
		while(!pq.isEmpty())
		{
			u=pq.remove();
			if(u.bound<minlength)
			{
				int lf=u.level+1;
				String[]temps=u.path.split("#");
				List<String>upaths=Arrays.asList(temps);
				
				for(int i=1;i<ca.length;i++)
				{
					if(!upaths.contains(i+""))
						{	
						Node v=new Node();
						v.path="";
						for(int m=0;m<u.path.length();m++)
						{
							v.path=v.path+u.path.charAt(m);
						}
						
						v.level=lf;
						v.path=v.path+"#"+i;
								
						v.bound=bound(v.path,ca);
						
						if(v.bound<minlength)
						{
							
							pq.add(v);
							ncount=ncount+1;
							//System.out.println(ncount+"****");
						}
						else
						{
							v=null;
						}
				
						
				}
					else
					{

						if(u.level==ca.length-1)
						{
							
							
							
							for(int k=0;k<ca.length;k++)
								{
								if(!u.path.contains(""+k))
									{
									u.path=u.path+"#"+k;
									}
								}
							
							u.path=u.path+"#"+0;
							
							if(Length(u.path,ca)<minlength)
							{
								minlength=Length(u.path,ca);
								opttour=u.path;
								minln=minlength;
							}
						}
						
						
					}
				}
			}
		}
		return opttour;
	}
	
	

	public static int Length(String p,int costm[][])
	{
		int pathlen=0;
		String x[]=p.split("#");
		for(int i=0;i<x.length-1;i++)
		{
			Integer k=new Integer(x[i]);
			Integer l=new Integer(x[i+1]);
			pathlen=pathlen+costm[k][l];
		}
		return pathlen;
	}
	
	
	
	public static int bound(String boundtour,int arrmat[][])
	{
		int sizeA=arrmat.length;
		
	
		String temp=new String();
		Integer min=Integer.MAX_VALUE;
		int lbound=0;
		
				
		if (boundtour.contains("#"))
		{
			String temptour[]=boundtour.split("#");
			for(int i=0;i<temptour.length-1;i++)
			{
				lbound=lbound + (arrmat[new Integer(temptour[i])][new Integer(temptour[i+1])]);
			}
		
		
			Integer lastv=new Integer(temptour[temptour.length-1]);
			
			//generating remaining nodes
			
			List<String>tempstring=Arrays.asList(temptour);
			for(int j=0;j<arrmat.length;j++)
			{
				if(!tempstring.contains(j+""))
				{
					temp+=j+"#";
				}
				
				
			}
		
			if(temp.trim().length()!=0)
			{
				temp=temp+"0";
			
			
			
			for(int k=0;k<temptour.length;k++)
			
				temptour[k]="";
			
			
			temptour=temp.split("#");
			
			
			
			//cost of last values in the node to remaining values in the path
			
			for(int xx=0;xx<temptour.length-1;xx++)
			{
				
				Integer minlb =Integer.MAX_VALUE;
			
				for(int y=0;y<temptour.length;y++)
				{
					Integer l=new Integer(temptour[xx]);
					Integer m=new Integer(temptour[y]);
					if(arrmat[l][m]<minlb&&arrmat[l][m]!=0)
					{
						minlb=arrmat[l][m];
					}
				}
				lbound=lbound+minlb;
			}
			}
			Integer minlb=Integer.MAX_VALUE;
			
			for(int i=0;i<(temptour.length)-1;i++)
			{
				Integer n=new Integer(temptour[i]);
				
				if(arrmat[lastv][n]!=0)
				{
					if(arrmat[lastv][n]<minlb)
					{
						minlb=arrmat[lastv][n];
					}
				}
			}
			lbound=lbound+minlb;
			
		}
		else
		{
			for(int i=0;i<sizeA;i++)
			{
				min=Integer.MAX_VALUE;
				for(int j=0;j<sizeA;j++)
				{
					if(arrmat[i][j]!=0)
					{
						if(arrmat[i][j]<min);
						min=arrmat[i][j];
					}
				}
				
				lbound=lbound+min;
			}
		}
		return lbound;
	}
				
	
	
		
}
	
	
	
	
	

class LowerBoundComparator implements Comparator<Node>
{
	public int compare(Node m,Node n)
	{
		if(m.bound<n.bound)
			return -1;
		if(m.bound>n.bound)
			return 1;
	return 0;	
	}

	
}


 class Node
{
	int level;
	String path;
	int bound;
}

