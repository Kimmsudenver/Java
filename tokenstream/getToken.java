package tokenstream;


import java.io.*;
import java.util.*;


import javax.swing.JFileChooser;

public class getToken {
	static Hashtable<String, Integer> special = new  Hashtable<String, Integer>();
	static Hashtable<String, Integer> likestr = new  Hashtable<String, Integer>();
	static BufferedReader read;
	public static void main(String arg[]){
		special=initiateInfo();
		likestr=initLikestr();
		String line;
		File fl=null;
			try {
				JFileChooser choose = new JFileChooser();
				int result=choose.showOpenDialog(null);
				if(result==JFileChooser.APPROVE_OPTION)
					fl=new File(choose.getSelectedFile().getPath());
				read=new BufferedReader(new FileReader(fl));
				//read each line and process getToken for each line
				while((line=read.readLine())!=null){
						getToken(line);
						System.out.println("EOL 27");
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	
	//This function get a string as input and return all the token 
	//and number of token in this string
	
	public static void getToken(String s){
		char[] a = s.toCharArray();		
		String result="";		
		for(int i=0;i<a.length;i++){
			result+=a[i];
			//see if special characters
			if(a[i]==':'){
				System.out.println(result+a[++i] +" 24");
				result="";
				continue;
			}
			if(special.get(""+a[i])!=null){				
				if(i==a.length-1||special.get(""+a[i]+a[i+1])==null){
					System.out.println(a[i]+" "+special.get(""+a[i]));
					result="";
					continue;
				}
				else{					
					System.out.println(""+a[i]+a[++i]+" "+special.get(""+a[i-1]+a[i]));
					result="";
					continue;
				}
			}
			
			//see if a string
			if(a[i]=='"'){		
				i++;
				while(a[i]!='"' && i <a.length){
					result+=a[i];		
					i++;
				}
				result+=a[i];
				System.out.println(result+" 30");
				result="";
				continue;
			}
			
			//see if a number
			if(isNumber(a[i])){
				while((isNumber(a[i+1]) || a[i+1]=='.')&& i <a.length-1){
					result+=a[++i];
				}
				System.out.println(result+" 29");
				result="";
				continue;
			}
			
			//else then it's a identifier or a reserved word
			else {
				if(result.equals(":")) continue; //special case of :=
				//if followed by special characters or space or it's the end of line. 
				if(i==a.length-1||(i+1<a.length)&&(special.get(a[i+1])!=null||Character.isWhitespace(a[i+1]))){					
					if(likestr.get(result)!=null){
						System.out.println(result+ " "+likestr.get(result)); 
						result="";
						continue;
					}
					else{
						System.out.println(result + " 28");
						result="";
						continue;
					}
				}						
			}
			
			
		}
	}		
			

		
	
	//This function check to see if a character is a number
	
	public static boolean isNumber(char s){
		 boolean ret = true;
		    try {

		        Double.parseDouble(""+s);

		    }catch (NumberFormatException e) {
		        ret = false;
		    }
		    return ret;
	}
	
	
	//create a hash table for special characters only
	public static Hashtable<String, Integer> initiateInfo(){
		special.put(".",11);
		special.put(")",12);
		special.put("(",13);
		special.put("/",14);
		special.put("*",15);
		special.put("-",16);
		special.put("+",17);
		special.put("<>",18);
		special.put(">",19);
		special.put(">=",20);
		special.put("=",21);
		special.put("<=",22);
		special.put("<",23);
		special.put(":=",24);
		special.put(";",25);
		special.put(" ",26);
		special.put("\\n",27);
		return special;
	}

	//create a hash table for reserved words only
	public static Hashtable<String, Integer> initLikestr(){
		likestr.put("IF",1);
		likestr.put("THEN",2);
		likestr.put("ELSE",3);
		likestr.put("FI",4);
		likestr.put("LOOP",5);
		likestr.put("BREAK",6);
		likestr.put("READ",7);
		likestr.put("PRINT",8);
		likestr.put("AND",9);
		likestr.put("OR",10);
		likestr.put("END",31);
		return likestr;
	}
	
	
}