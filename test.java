import java.io.*;
import java.util.regex.*;
import java.util.*;
import java.math.*;
import java.text.*;

public class test
{
static String [] UnitName  =new String[20];//长度单位的名称
static String [] UnitValue=new String[20];//长度单位与国际单位的换算关系
static String [] MathExpression=new String[20];//长度单位混合运算的表达式
static String [][] ElementOfExpression=new String[20][];//数学表达式中元素的个数
static double [] ResultOfExpression=new double[20];//每个表达式的值
static String [] ResultString=new String[20];
static String [][] OperatorElement=new String[20][20];//表达式元素前的算数运算符
static int i=0;
static int n=0;//需要 求职表达式的个数

public static void main(String args[])
{

try{

  String indir=System.getProperty("user.dir");
	indir=indir+"\\input.txt";
	File f=new File(indir);
	String outdir=System.getProperty("user.dir");
	outdir=outdir+"\\output.txt";
	File out=new File(outdir);
	BufferedReader d=new BufferedReader(new FileReader(f));
	BufferedWriter outbuffer=new BufferedWriter(new FileWriter(out));
	outbuffer.write("992134601@qq.com");
	outbuffer.newLine();
	outbuffer.newLine();

	String str=new String("");
	while((str=d.readLine())!=null)
	{
		getUnitNameAndExpression(str);
	}
	result();
	for(int tp=0;tp<n;tp++)
	{
		outbuffer.write(ResultString[tp]);
		outbuffer.newLine();
	}
	outbuffer.flush();
	outbuffer.close();
	d.close();
	}
catch(IOException e){System.out.println(e);}

}
public static void result()//类函数，将表达式所求得的值精确到两位小数，并转成字符串
	{

		for(int k=0;k<n;k++)
		{
			ElementOfExpression[k]=MathExpression[k].split("\\+|\\-");
		}
		getOperator();
		DecimalFormat df=new DecimalFormat(".##");
		for(int totalnumExpression=0;totalnumExpression<n;totalnumExpression++)
		{
			calculateExpression(totalnumExpression);
			ResultString[totalnumExpression]=df.format(ResultOfExpression[totalnumExpression]);
			ResultString[totalnumExpression]=ResultString[totalnumExpression]+" m";
		}

	}
public static void calculateExpression(int order)//将表达式切割成若干元素（数值+单位），调用类函数calculateElement(),计算元素值，并根据元素前的算数运算符，相加减
	{                                            //得出的结果送result（）函数处理
		ResultOfExpression[order]=0.0;
		ResultOfExpression[order]=ResultOfExpression[order]+calculateElement(order,0);
		for(int rTemp=1;rTemp<ElementOfExpression[order].length;rTemp++)
			{
			double ReTemp=calculateElement(order,rTemp);
			if(OperatorElement[order][rTemp]=="+")
				ResultOfExpression[order]=ResultOfExpression[order]+ReTemp;
			else if(OperatorElement[order][rTemp]=="-")
				ResultOfExpression[order]=ResultOfExpression[order]-ReTemp;
			}
	}

public static void getOperator()//确定每一个算数表达式每个元素的数学运算符
	{
		for(int numExpression=0;numExpression<n;numExpression++)
		{
			byte [] tempByte=(MathExpression[numExpression]).getBytes();
			int orderOperator=1;
			for(int nu=0;nu<tempByte.length;nu++)
			{
				if(tempByte[nu]=='+')
					OperatorElement[numExpression][orderOperator++]="+";
				else if(tempByte[nu]=='-')
					OperatorElement[numExpression][orderOperator++]="-";
			}
		}
	}
public static void getUnitNameAndExpression(String str)//类函数，提取单位换算关系并赋值给类变量，同时获得表达式
	{
		if(str.indexOf('=')!=-1)
		{
			UnitName[i]=str.substring(str.indexOf('1')+1,str.indexOf('='));
			UnitValue[i]=str.substring(str.indexOf('=')+1,str.indexOf("meters"));
			UnitName[i]=UnitName[i].trim();
			UnitValue[i]=UnitValue[i].trim();
			i++;
		}
		else if(str.length()!=0)
		{
			MathExpression[n]=str;
			n++;
		}
	}

public static double calculateElement(int a,int b)//类函数，返回元素的double数值
	{                                                 //a is the order of Expression, b is order element of expression a 
		double ResultTest=0;
		String []temp=ElementOfExpression[a][b].split(" ");
		temp[0]=temp[0].trim();
		temp[1]=temp[1].trim();
		for(int k2=0;k2<UnitName.length+1;k2++)
		{
			if(UnitName[k2].equals(temp[1]))
			{
				double Result1=Double.valueOf(UnitValue[k2]);
				double Result2=Double.valueOf(temp[0]);
				ResultTest=Result1*Result2;
				break;
			}
		}
		return ResultTest;
	}

}
