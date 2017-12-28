package container;

import java.util.ArrayList;
import java.util.function.BiFunction;


public interface IContainer extends Iterable<Person>{
	/**
	* @Author: Shu Wang
	* @Desciption: 插入一个新的任务，可能抛出任务已经存在的异常
	* @param p : 人物
	* @Date:15:48 2017/12/27
	*/
	void insert(Person p) throws PersonExistsException;

	/**
	* @Author: Shu Wang
	* @Desciption: 根据一个谓词查找所有满足条件的任务集合
	 * @param p : 需要进行比较的对象。所有的结果满足p中的某个条件
	 *@param pred : 比较的二元谓词
	* @Date:15:49 2017/12/27
	*/
	ArrayList<Person> query(Person p, BiFunction<Person, Person, Boolean> pred);
	/**
	* @Author: Shu Wang
	* @Desciption:删除一个任务对象。可能抛出任务不存在的错误
	 * @param p :人物
	* @Date:15:52 2017/12/27
	*/
	void delete(Person p) throws PersonNotFoundException;
	/**
	* @Author: Shu Wang
	* @Desciption: 修改一个对象
	 * @param p : 被修改的人物对象
	 * @param n : p中的字段与n相同。n不会被保存在集合中。
	* @Date:15:53 2017/12/27
	*/
	void modify(Person p, Person n)throws PersonNotFoundException;
	/**
	* @Author: Shu Wang
	* @Desciption: 显示所有的人物的数量
	* @Date:15:55 2017/12/27
	*/
	int count();
}
