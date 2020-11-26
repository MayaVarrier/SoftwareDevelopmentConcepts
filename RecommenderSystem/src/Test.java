
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    CourseSelector cs= new CourseSelector();
    cs.read("C:\\Users\\hp\\Documents\\MACS Dalhousie\\SDC\\Assignment 1\\SD1.txt");
    //cs.read("C:\\Users\\hp\\Documents\\MACS Dalhousie\\SDC\\Assignment 1\\SD1.txt");
    //cs.showCommon("CSCI1110 CSCI2110 csci2112 CSCI2122 CSCI2134 csci2141 CSCI3171");
    //cs.showCommon(null);
    //cs.showCommon("csci2112 btds4758 CSCI1110 kmng5748 CSCI2110 nahy2475 CSCI2122 ghimmkkd maaj4756 CSCI2134 abcs3575 csci2141 CSCI3171");
    //cs.showCommonAll("C:\\Users\\hp\\Documents\\MACS Dalhousie\\SDC\\Assignment 1\\StudentData2.txt");
    //cs.showCommonAll(null);
    //cs.recommend("CSCI2110 CSCI2122 CSCI1110 CSCI2112 CSCI2134 CSCI3171 CSCI2141",0, 1);
    cs.recommend("ABC IUJ", 2, 2);
    //cs.recommend("CSCI2110 CSCI2122", 1, 10);
	}
	
	/*	for(String[] a : a1)
	{
		String ch[] = new String[a.length];
		for(int i=0;i<a.length;i++)
		{	 
	         ch[i] = a[i]; 
	         int find = 1; 
	         for (int j = 0; j <= i; j++) { 
	                // If any matches found 
	                if (a[i] == ch[j])  
	                    find++;                 
	            } 
		}
	}	
		
	
	String ch[] = new String[courses.length()]; 
	ch = (courses.split(" "));
	for(int i=0;i<courses.length();i++)
	{	 
		int find = 1; 
		for (int j = 0; j <= i; j++) 
			{ 
        // If any matches found 
			if (ch[i] == ch[j])  
				find++;                 
			}  
		System.out.println("Occurance of " +ch[i]+ "is : " +find );
    } 
	System.out.println("");*/

}



