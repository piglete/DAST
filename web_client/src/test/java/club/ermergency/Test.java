package club.ermergency;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2019-01-02
 * Time: 10:28
 * Description:
 */
public class Test {


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
//        List list = new ArrayList();
//        list.add("a");
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("d");
//        list.stream().filter(a -> !a.equals("")).distinct().forEach(a -> {
//            System.out.println(a);
//        });
//        Map<String, Object> map = new HashMap<>();
//        map.put("aa", 1);
//        map.put("bb", 2);
//        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
//            System.out.println("key :" + stringObjectEntry.getKey() + "  ,   value :" + stringObjectEntry.getValue());
//        }
//        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String dateTime = sdf.format(LocalDateTime.now());
//        System.out.println(dateTime);
//        Integer a = 101, b = 102;
//        swap(a, b);
//        System.out.println("a =" + a + "   b =" + b);

    }

    @org.junit.Test
    public void testTfdsf(){
//        System.out.println(redisTemplate.opsForValue().get("key1"));
//        redisTemplate.opsForValue().set("key1", "value1");
//        System.out.println(redisTemplate.opsForValue().get("key1"));
    }
    public static void swap(Integer i, Integer j) throws NoSuchFieldException, IllegalAccessException {
//        int temp = i;
//        i = j;
//        j = temp;
        Field value = Integer.class.getDeclaredField("value");
        int temp = i.intValue();
        value.setAccessible(true);
        value.setInt(i, j.intValue());
        value.setInt(j, temp);

    }

    private static Test person = new Test();
    public static Test getOnePerson() {
        try {
            return (Test) person.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        Test person = new Test();
        return null;
    }
}


