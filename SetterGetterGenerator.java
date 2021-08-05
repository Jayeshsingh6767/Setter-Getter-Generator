package com.thinking.machines.utils;
import java.lang.reflect.*;
import java.io.*;
public class SetterGetterGenerator
{
public static void main(String gg[])
{
if(gg.length!=2 && gg.length!=1)
{
System.out.println("Usage: javac -classpath class_path;. SetterGetterGenerator class_name constructor=false/true");
return;
}
if(gg.length==2)
{
if(!(gg[1].equalsIgnoreCase("constructor=true")||gg[1].equalsIgnoreCase("constructor=false")))
{
System.out.println("Usage: javac -classpath class_path;. SetterGetterGenerator class_name constructor=false/true");
return;
}
}
try
{
Class c=Class.forName(gg[0]);
Field fields[];
fields=c.getDeclaredFields();
Field field;
String setterName;
String getterName;
String tmp;
String line;
String fieldName;
String fieldTypeName;
String emptyString="";
TMArrayList<String> list=new TMArrayList<String>();
Class fieldType;
if(gg.length==1||gg[1].equalsIgnoreCase("constructor=true"))
{
line="public "+c.getName()+"()";
list.add(line);
list.add("{");
for(int k=0;k<fields.length;k++)
{
field=fields[k];
fieldName=field.getName();
fieldType=field.getType();
fieldTypeName=fieldType.getSimpleName();
line="this."+fieldName+"="+defaultValueOf(fieldTypeName)+";";
list.add(line);
}//for loops ends
list.add("}");
}
for(int e=0;e<fields.length;e++)
{
field=fields[e];
fieldName=field.getName();
fieldType=field.getType();
if(fieldName.charAt(0)>=97||fieldName.charAt(0)<=122)
{
tmp=fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
}
else
{
tmp=fieldName;
}
setterName="set"+tmp;
getterName="get"+tmp;
line="public void "+setterName+"("+fieldType.getName()+" "+fieldName+")";
list.add(line);
list.add("{");
line="this."+fieldName+"="+fieldName+";";
list.add(line);
list.add("}");
line="public "+fieldType.getName()+" "+getterName+"()";
list.add(line);
list.add("{");
line="return this."+fieldName+";";
list.add(line);
list.add("}");
}//for loops ends
File file=new File("tmp.tmp");
if(file.exists() ) file.delete();
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
TMIterator<String> i=list.iterator();
while(i.hasMoreElement())
{
randomAccessFile.writeBytes(i.next()+"\r\n");
}//while ends 
randomAccessFile.close();
System.out.println("setter/getters for "+c.getName()+" is generated in tmp.tmp file");
}catch(ClassNotFoundException cnfe)
{
System.out.println("Class not found");
}
catch(IOException ioe)
{
System.out.println(ioe.getMessage());
}
}//main ends
private static String defaultValueOf(String fieldTypeName)
{
if(fieldTypeName.equals("Long")||fieldTypeName.equals("long")) return "0";
if(fieldTypeName.equals("Integer")||fieldTypeName.equals("int")) return "0";
if(fieldTypeName.equals("Short")||fieldTypeName.equals("short")) return "0";
if(fieldTypeName.equals("Byte")||fieldTypeName.equals("byte")) return "0";
if(fieldTypeName.equals("Double")||fieldTypeName.equals("double")) return "0.0";
if(fieldTypeName.equals("Float")||fieldTypeName.equals("float")) return "0.0f";
if(fieldTypeName.equals("Character")||fieldTypeName.equals("char")) return "' '";
if(fieldTypeName.equals("Boolean")||fieldTypeName.equals("boolean")) return "false";
if(fieldTypeName.equals("String")) return "\"\"";
else return null;
}
}//class ends

/*if(fieldTypeName.equals("int") || fieldTypeName.equals("short") ||fieldTypeName.equals("long") || fieldTypeName.equals("byte") ||fieldTypeName.equals("char"))
{
line="this."+fieldName+"=0;";
list.add(line);
}else if(fieldTypeName.equals("double") || fieldTypeName.equals("float") )
{
line="this."+fieldName+"=0.0;";
list.add(line);
}else if(fieldTypeName.equals("boolean"))
{
line="this."+fieldName+"=false;";
list.add(line);
}else if(fieldTypeName.equals("java.lang.String"))
{
line="this."+fieldName+"="+emptyString+";";
list.add(line);
}else
{
line="this."+fieldName+"=null;";
list.add(line);
}*/
