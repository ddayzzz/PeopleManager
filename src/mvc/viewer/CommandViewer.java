package mvc.viewer;
import common.PersonType;
import mvc.controler.Controler;
import mvc.model.Model;
import container.IContainer;
import container.Person;
import persons.Student;
import persons.Worker;

import java.awt.event.ActionListener;
import java.util.*;

public class CommandViewer implements Viewer {

	private Person queried_person;
	private PersonType viewType;
	public CommandViewer(PersonType viewType){
		this.viewType = viewType;
	}

	@Override
	public void showInfoMessage(String msg) {
		System.out.println(msg);
	}

	@Override
	public void showCriticalMessage(String msg) {
		System.err.println(msg);
	}

	@Override
	public Person getNewPersonFromUserInput(boolean idEditable, Person oldPerson) {
		//先测试学生的
		assert(idEditable== false && oldPerson !=null);
		Scanner scanner= new Scanner(System.in);
		String id="", name;
		if(oldPerson != null)
		{
			//System.out.println("原始的信息：" + oldPerson);
			id = oldPerson.getId();
		}

		int age;
		System.out.println("请输入姓名：");
		name = scanner.nextLine();
		if(idEditable == true)//可以不修改ID
		{
			System.out.println("请输入ID：");
			id = scanner.next();//ID不会有空格
		}
		System.out.println("请输入年龄：");
		age = scanner.nextInt();
		if(viewType == PersonType.Student)
		{
			float grade;
			System.out.println("请输入成绩：");
			grade = scanner.nextFloat();
			return new Student(id, name, age, grade);
		}
		else{
			//工人
			String job;
			float salary;
			scanner.nextLine();//清除age的多余回车
			System.out.println("请输入工作：");
			job = scanner.nextLine();
			System.out.println("请输入工资：");
			salary = scanner.nextFloat();
			return new Worker(id, name, age, job, salary);
		}
	}

	@Override
	public Person getSelectedPerson() {
		return queried_person;
	}

	@Override
	public String getUserInput(String title, String text) {
		//输入一些文本
		System.out.println(title + ":");
		System.out.println(text);
		Scanner scanner= new Scanner(System.in);
		String id;
		id = scanner.nextLine();
		return id;
	}

	@Override
	public void changeTitleText(String title) {
		System.out.println(title);
	}

	@Override
	public void showAllPersons(IContainer c) {
		for(Object person : c)
		{
			System.out.println(person);
		}
	}

	@Override
	public void showPersonInfo(Person p) {
		System.out.println("学生：" + p);
	}

	@Override
	public void setSelectedPerson(Person person) {
		queried_person = person;
	}

	@Override
	public boolean askUserYesOrNo(String text) {
		System.out.println(text + "（Y,y/N）");
		Scanner scanner = new Scanner(System.in);
		String res = scanner.next();
		if(res.equals("Y") || res.equals("y"))
			return true;
		else
			return false;
	}
	@Override
	public boolean askToRefreshList() {
		return askUserYesOrNo("是否立即更新视图？");
	}

	@Override
	public int askManyOptions(String title, String text, String[] optionsText) {
		if(optionsText == null || optionsText.length == 0)
			return -1;
		showInfoMessage(title + ":");
		showInfoMessage(text);
		for(int i=0;i<optionsText.length;++i)
			showInfoMessage("\t" + i + "." + optionsText[i]);
		Scanner scanner = new Scanner(System.in);
		int code = scanner.nextInt();
		if(code >= 0 && code < optionsText.length)
			return code;
		else
			return -1;//非法的数字
	}

	@Override
	public String showQueryPersonsResultAndGetSelectResult(String title, String text, String[] res) {
		if(res == null || res.length == 0)
			return null;
		showInfoMessage(title + ":");
		showInfoMessage(text + "（请输入编号）");
		for(int i=0;i<res.length;++i)
			showInfoMessage("\t" + i + "." + res[i]);
		Scanner scanner = new Scanner(System.in);
		int code = scanner.nextInt();
		if(code >= 0 && code < res.length)
			return res[code];
		else
			return null;//非法的数字
	}

	@Override
	public boolean needAddBuutonActionListener() {
		return false;
	}

	@Override
	public void addActionListenerToButtons(ActionListener actionListener) {

	}
}
