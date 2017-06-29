package kvstore;

public class kvs {

	public static void main(String[] args) {
		maintainkv kvs = new maintainkv();
		
		kvs.add(1, "a");
		kvs.add(2, "b");
		
		kvs.delete(2);
		kvs.commit();
		
		kvs.printMap();
		
		kvs.add(3, "c");
		kvs.commit();
		kvs.add(2, "b1");
		kvs.add(1, "a1");
		
		kvs.rollback();
		
		kvs.printMap();

	}

}
