import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smart.bean.OrderItem;
import com.smart.bean.Orders;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONTest {
	public static void main(String[] args) {
		System.out.println("JavaBean转换成JSON");
		Orders orders=new Orders();
		orders.setOrder_id(1);
		orders.setOrder_state(1);
		orders.setOrder_time("2017-04-15 14:26:25");
		orders.setTable_id(2);
		orders.setTotal_price(1000);
		JSONObject jsonObject=JSONObject.fromObject(orders);
		System.out.println(jsonObject.toString());
		
		orders=(Orders) jsonObject.toBean(jsonObject, Orders.class);
		
		System.out.println("JavaBean转换成JSONArray");
		OrderItem orderItem=new OrderItem();
		orderItem.setFood_id(1);
		orderItem.setFood_num(1);
		orderItem.setFood_state(1);
		orderItem.setOrder_id(1);
		
		OrderItem orderItem2=new OrderItem();
		orderItem2.setFood_id(1);
		orderItem2.setFood_num(1);
		orderItem2.setFood_state(1);
		orderItem2.setOrder_id(1);
		
		OrderItem orderItem3=new OrderItem();
		orderItem3.setFood_id(1);
		orderItem3.setFood_num(1);
		orderItem3.setFood_state(1);
		orderItem3.setOrder_id(1);
		
		OrderItem orderItem4=new OrderItem();
		orderItem4.setFood_id(1);
		orderItem4.setFood_num(1);
		orderItem4.setFood_state(1);
		orderItem4.setOrder_id(1);
		List<OrderItem>list=new ArrayList<>();
		list.add(orderItem);
		list.add(orderItem2);
		list.add(orderItem3);
		list.add(orderItem4);
		JSONArray jsonArray=JSONArray.fromObject(list);
		System.out.println(jsonArray.toString());
		
		Map<String,String>map=new HashMap<>();
		map.put("order", jsonObject.toString());
		map.put("order_item", jsonArray.toString());
		JSONObject jsonObject2=JSONObject.fromObject(map);
		System.out.println(jsonObject2);
		
		
		String string=jsonObject2.get("order").toString();
		System.out.println(string);
		
	}
}
