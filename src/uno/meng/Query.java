package uno.meng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import uno.meng.db.DBHelper;
public class Query {
	static String sql = new String();  
    static DBHelper db = null;  
    static ResultSet ret = null;  
    final static String employee = "EMPLOYEE"; 
    final static String department = "DEPARTMENT";
    final static String works_on = "WORKS_ON";
    final static String project = "PROJECT";
    static String command = new String();
	@SuppressWarnings("null")
	public static void main(String[] args) throws IOException {
		while(true){
				System.out.print("MengSQL>");
				BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));  
				command = strin.readLine();
				command = command.replaceAll("\\s+", " ");
				if(command.trim().equals("quit") || command.trim().equals("exit")){
					System.out.println("谢谢使用！");
					break;
				}
				String EachCMD[] = new String[5];
				int index = 0;
				int head = 0;
				for(int i =0;i<command.length();i++){
					if(command.charAt(i) == ' ' && index < 4){
						EachCMD[index] = command.substring(head,i);
						head = i+1;
						index++;
					}else if(index == 4){
						EachCMD[index] = command.substring(head,command.length());
					}
				}
				int cmd = Integer.parseInt(EachCMD[2]);
				String params[] = EachCMD[4].substring(1,EachCMD[4].length()-1).split("\\,");
				switch(cmd){
					case 1:
						EmployeeNum(params);
						break;
					case 2:
						EmployeeName(params);
						break;
					case 3:
						EmployeeWorkAT(params);
						break;
					case 4:
						EmployeeWorkATandLowSalary(params);
					   break;
					case 5:
						EmployeeNotIn(params);
						break;
					case 6:
						EmployeeOf(params);
						break;
					case 7:
						EmployeeIN(params);
						break;
					case 8:
						EmployeeSalaryLow(params);
						break;
					case 9:
						break;
					default:
						System.out.println("暂时没有"+cmd+"号指令！");
						break;
				}
		}
	}
	//参加了项目编号为%PNO%的项目的员工号
	public static void EmployeeNum(String[] params){
		int num = 0;
		sql = "select ESSN from "+works_on+" where PNO = \""+params[0]+"\"";//SQL语句  
        db = new DBHelper(sql);//创建DBHelper对象  
        try {  
            ret = db.pst.executeQuery();//执行语句，得到结果集  
            System.out.println("+------------+");
            System.out.println("|    ESSN    |");
            System.out.println("+------------+");
            while (ret.next()){  
                System.out.println("| "+ret.getString("ESSN")+" |");
                num++;
            }//显示数据  
            System.out.println("+------------+");
            System.out.println("查询出"+num+"行数据！");
            ret.close();  
            db.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }		
	}
     //参加了项目名为%PNAME%的员工名字
	public static void EmployeeName(String[] params){
		int num = 0;
		sql = "select ENAME from "+employee+","+project+","+works_on+" where PNAME = \""+params[0]+"\"" +" and PROJECT.PNO = WORKS_ON.PNO and EMPLOYEE.ESSN = WORKS_ON.ESSN";//SQL语句  
        db = new DBHelper(sql);//创建DBHelper对象  
        try {  
            ret = db.pst.executeQuery();//执行语句，得到结果集  
            System.out.println("+------------+");
            System.out.println("|    ENAME   |");
            System.out.println("+------------+");
            while (ret.next()){  
                System.out.println("|  "+ret.getString("ENAME")+"  |");
                num++;
            }//显示数据  
            System.out.println("+------------+");
            System.out.println("查询出"+num+"行数据！");
            ret.close();  
            db.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }	
	}
	//在%DNAME%工作的所有工作人员的名字和地址
	public static void EmployeeWorkAT(String[] params){
		int num = 0;
		sql = "select ENAME,ADDRESS from "+employee+","+department+" where DNAME = \""+params[0]+"\"" +" and EMPLOYEE.DNO = DEPARTMENT.DNO";//SQL语句  
        db = new DBHelper(sql);//创建DBHelper对象  
        try {  
            ret = db.pst.executeQuery();//执行语句，得到结果集  
            System.out.println("+----------------+------------------------+");
            System.out.println("|    ENAME       |		ADDRESS   |");
            System.out.println("+----------------+------------------------+");
            while (ret.next()){  
                System.out.println("|     "+ret.getString("ENAME")+" 	 |  "+ret.getString("ADDRESS")+"	|");
                num++;
            }//显示数据  
            System.out.println("+----------------+------------------------+");
            System.out.println("查询出"+num+"行数据！");
            ret.close();  
            db.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }	
	}
	//在%DNAME%工作且工资低于%SALARY%元的员工名字和地址
	public static void EmployeeWorkATandLowSalary(String[] params){
		int num = 0;
		sql = "select ENAME,ADDRESS from "+employee+","+department+" where SALARY < "+Integer.parseInt(params[1])+" and DNAME = \""+params[0]+"\"" +" and EMPLOYEE.DNO = DEPARTMENT.DNO";//SQL语句  
        db = new DBHelper(sql);//创建DBHelper对象  
        try {  
            ret = db.pst.executeQuery();//执行语句，得到结果集  
            System.out.println("+----------------+------------------------+");
            System.out.println("|    ENAME       |		ADDRESS   |");
            System.out.println("+----------------+------------------------+");
            while (ret.next()){  
                System.out.println("|     "+ret.getString("ENAME")+" 	 |  "+ret.getString("ADDRESS")+"	|");
                num++;
            }//显示数据  
            System.out.println("+----------------+------------------------+");
            System.out.println("查询出"+num+"行数据！");
            ret.close();  
            db.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }	
	}
	//没有参加项目编号为%PNO%的项目的员工姓名
	public static void EmployeeNotIn(String[] params){
		int num = 0;
		sql = "select ENAME from "+employee+" where EMPLOYEE.ESSN not in ( select ESSN from WORKS_ON where PNO = \""+params[0]+"\")";//SQL语句  
        db = new DBHelper(sql);//创建DBHelper对象  
        try {  
            ret = db.pst.executeQuery();//执行语句，得到结果集  
            System.out.println("+------------+");
            System.out.println("|    ENAME   |");
            System.out.println("+------------+");
            while (ret.next()){  
                System.out.println("|  	"+ret.getString("ENAME")+"  |");
                num++;
            }//显示数据  
            System.out.println("+------------+");
            System.out.println("查询出"+num+"行数据！");
            ret.close();  
            db.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }	
	}
	//由%ENAME%领导的工作人员的姓名和所在部门的名字
	public static void EmployeeOf(String[] params){
		int num = 0;
		sql = "select ENAME,DNAME from "+employee+","+department+" where EMPLOYEE.DNO = DEPARTMENT.DNO and DEPARTMENT.MGRSSN = ( select ESSN from EMPLOYEE where ENAME = \""+params[0]+"\") and EMPLOYEE.SUPERSSN = DEPARTMENT.MGRSSN";//SQL语句  
        db = new DBHelper(sql);//创建DBHelper对象  
        try {  
            ret = db.pst.executeQuery();//执行语句，得到结果集  
            System.out.println("+----------------+------------------------+");
            System.out.println("|    ENAME       |		DNAME	   |");
            System.out.println("+----------------+------------------------+");
            while (ret.next()){  
                System.out.println("|     "+ret.getString("ENAME")+" 	 |  "+ret.getString("DNAME")+"	|");
                num++;
            }//显示数据  
            System.out.println("+----------------+------------------------+");
            System.out.println("查询出"+num+"行数据！");
            ret.close();  
            db.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
	}
	//至少参加了项目编号为%PNO1%和%PNO2%的项目的员工号
	public static void EmployeeIN(String[] params){
		int num = 0;
		sql = "select ESSN from "+works_on+" where PNO = \""+params[0]+"\" and ESSN in (select ESSN from WORKS_ON where PNO = \""+params[1]+"\")";//SQL语句  
        db = new DBHelper(sql);//创建DBHelper对象  
        try {  
            ret = db.pst.executeQuery();//执行语句，得到结果集  
            System.out.println("+------------+");
            System.out.println("|    ESSN    |");
            System.out.println("+------------+");
            while (ret.next()){  
                System.out.println("| "+ret.getString("ESSN")+" |");
                num++;
            }//显示数据  
            System.out.println("+------------+");
            System.out.println("查询出"+num+"行数据！");
            ret.close();  
            db.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }	
	}
	//员工平均工资低于%SALARY%元的部门名称
	public static void EmployeeSalaryLow(String[] params){
		int num = 0;
		sql = "select DNAME from "+department+","+employee+" where EMPLOYEE.DNO = DEPARTMENT.DNO group by EMPLOYEE.DNO having avg(SALARY) < "+Integer.parseInt(params[0]);//SQL语句  
        db = new DBHelper(sql);//创建DBHelper对象  
        try {  
            ret = db.pst.executeQuery();//执行语句，得到结果集  
            System.out.println("+------------------------+");
            System.out.println("|    	DNAME		 |");
            System.out.println("+------------------------+");
            while (ret.next()){  
                System.out.println("|  "+ret.getString("DNAME")+"    |");
                num++;
            }//显示数据  
            System.out.println("+------------------------+");
            System.out.println("查询出"+num+"行数据！");
            ret.close();  
            db.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }	
	}
}
