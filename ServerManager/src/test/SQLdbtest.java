package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import db.Db;
import exceptions.NegativeNumberNotValid;

public class SQLdbtest {



	private static Scanner scanner;

	public static void main(String[] args) {
		 
		scanner = new Scanner(System.in);
		
		boolean On = true;
			
		Calendar cal = GregorianCalendar.getInstance();
		
		do {
			String employid = null;
			
			showMenu();

			String option=scanner.nextLine();
			
			switch (option) {
			case "1":
				try {
					
					ResultSet rs = Db.get("SELECT * FROM EMPLOYEES");
					
					while(rs.next()) {
						System.out.println();
						System.out.println();
						System.out.println("Empleado: " + rs.getInt(1));
						System.out.println("\tNombre: " + rs.getString(2)+" "+rs.getString(3));
						System.out.println("\tEmail: " + rs.getString(4));
						System.out.println("\tPhone number: " + rs.getString(5));
						System.out.println("\tHire Date: " + rs.getDate(6));
						System.out.println("\tSalary : " + rs.getFloat(8) );
					}
					
					rs.close();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					//
				}
				finally{
					try {
						Db.disconnect();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						 
					}
				}
				break;
				
			case "2":
				
				try {
					
					System.out.println("Introduzca el numero de empleado que desea modificar");
			
					 employid = scanner.nextLine();
					
					System.out.println();
					
					if (Integer.parseInt(employid)<0) {
						try {
							throw new NegativeNumberNotValid();
						} catch (NegativeNumberNotValid e){
							main(args);;
						}
					}else{
						ResultSet rs = Db.get("SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID='"+employid+"'");
						
						while(rs.next()) {
							System.out.println();
							System.out.println();
							System.out.println("Empleado: " + rs.getInt(1));
							System.out.println("\tNombre: " + rs.getString(2)+" "+rs.getString(3));
							System.out.println("\tEmail: " + rs.getString(4));
							System.out.println("\tPhone number: " + rs.getString(5));
							System.out.println("\tHire Date: " + rs.getDate(6));
							System.out.println("\tSalary : " + rs.getFloat(8) );
						}
						rs.close();
						Db.disconnect();
					}
					
					System.out.println("NAME");
					String name = scanner.nextLine();
					System.out.println("APELLIDOS");
					String lname = scanner.nextLine();
					System.out.println("EMAIL");
					String email = scanner.nextLine();
					System.out.println("PHONE");
					String phone = scanner.nextLine();
					System.out.println("SALARIO");
					String salary = scanner.nextLine();
					
					System.out.println("Numero de filas modificas: "+Db.update("UPDATE EMPLOYEES "
							+ "SET FIRST_NAME = '"+name+"',"
							+ " LAST_NAME = '"+lname+"',"
							+ "EMAIL = '"+email+"', "
							+ "PHONE_NUMBER='"+phone+"', "
							+ "SALARY='"+salary+"' "
							+ "WHERE EMPLOYEE_ID='"+employid+"'"));
					
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					
				}
				finally{
					try {
						Db.disconnect();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						
					}
				}
				break;
			case "3":
				
				try {
					System.out.println("\n");
													
					System.out.println("ID");
					String id = scanner.nextLine();
					System.out.println("NAME");
					String name = scanner.nextLine();
					System.out.println("APELLIDOS");
					String lname = scanner.nextLine();
					System.out.println("EMAIL");
					String email = scanner.nextLine();
					System.out.println("PHONE");
					String phone = scanner.nextLine();
					System.out.println("SALARIO");
					String salary = scanner.nextLine();
					
					System.out.println("Numero de filas añadidas: "+Db.insert("INSERT INTO EMPLOYEES "
						+ "VALUES ("+id+",'"+name+"','"+lname+"','"+email+"','"+phone+"',"
						+ "TO_DATE('"+cal.get(GregorianCalendar.DAY_OF_MONTH)+"/"+cal.get(GregorianCalendar.MONTH)+"/"+cal.get(GregorianCalendar.YEAR)+"','DD/MM/YYYY'),"
						+ "'IT_PROG',"+salary+",null,null,null)"));	

				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					
				}
				finally{
					try {
						Db.disconnect();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						
					}
				}
				break;
				
			case "4":
				try {
					System.out.println("Introduzca el numero de empleado que desea borrar");
					
					employid = scanner.next();
					
					if (Integer.parseInt(employid)<0) {
						try {
							throw new NegativeNumberNotValid();
						} catch (NegativeNumberNotValid e){
							main(args);
						}
					}
					
					int rs = Db.delete("DELETE FROM EMPLOYEES WHERE EMPLOYEE_ID='"+employid+"'");
					
					if (rs>0) {
						System.out.println("Borrado!");
					}else{
						System.out.println("No se a podido borrar nada!");
					}
					
					
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					
				}
				finally{
					try {
						Db.disconnect();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						
					}
				}
				break;
				
			case "0":
				On=false;
				break;
				
			default:
				System.out.println("Opcion Incorrecta");
				break;
			}
			
		} while (On);

	}
	
	private static void showMenu() {
		System.out.println("MENU");
		System.out.println("1- Mostrar Employees");
		System.out.println("2- Modificar un empleado.");
		System.out.println("3- Resgistrar nuevo empleado");
		System.out.println("4- Borrar Employee");
		System.out.println("0- Salir");
	}

}
