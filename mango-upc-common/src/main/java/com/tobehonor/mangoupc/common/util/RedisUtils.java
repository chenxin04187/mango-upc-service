package com.tobehonor.mangoupc.common.util;

import com.tobehonor.mangoupc.common.config.SpringContext;
import com.tobehonor.mangoupc.common.exception.MangoApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author William Chan
 * @since 2022/8/6
 */
@Slf4j
public final class RedisUtils {
    
    private static final RedisTemplate<String, Object> redisTemplate = SpringContext.getBean("redisTemplate");
    
    private RedisUtils() {
    }
    
    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public static void expire(String key, long time) {
        if (time > 0) {
            assert redisTemplate != null;
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }
    
    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(String key) {
        assert redisTemplate != null;
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (expire == null) {
            return -1L;
        }
        return expire;
    }
    
    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key) {
        assert redisTemplate != null;
        Boolean isSuccess = redisTemplate.hasKey(key);
        if (isSuccess == null) {
            return false;
        }
        return isSuccess;
    }
    
    /**
     * 删除缓存
     *
     * @param key 可以传一个值或多个
     */
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            assert redisTemplate != null;
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }
    // ============================String=============================
    
    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        if (key == null) {
            return null;
        } else {
            assert redisTemplate != null;
            return redisTemplate.opsForValue().get(key);
        }
    }
    
    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        assert redisTemplate != null;
        redisTemplate.opsForValue().set(key, value);
    }
    
    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                assert redisTemplate != null;
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return 递增的值
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RedisDecreasingFactorException("递减因子必须大于0" + ", decreasing factor: " + delta);
        }
        assert redisTemplate != null;
        Long increment = redisTemplate.opsForValue().increment(key, delta);
        if (increment == null) {
            return -1L;
        }
        return increment;
    }
    
    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return 递减的值
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RedisDecreasingFactorException("递减因子必须大于0" + ", decreasing factor: " + delta);
        }
        assert redisTemplate != null;
        Long increment = redisTemplate.opsForValue().increment(key, -delta);
        if (increment == null) {
            return -1L;
        }
        return increment;
    }
    // ================================Map=================================
    
    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object hget(String key, String item) {
        assert redisTemplate != null;
        return redisTemplate.opsForHash().get(key, item);
    }
    
    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hmget(String key) {
        assert redisTemplate != null;
        return redisTemplate.opsForHash().entries(key);
    }
    
    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public static boolean hmset(String key, Map<String, Object> map) {
        try {
            assert redisTemplate != null;
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.warn("置入失败 key: " + key + " cause: ", e);
            return false;
        }
    }
    
    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public static boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            assert redisTemplate != null;
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.warn("置入失败 key: " + key + " cause: ", e);
            return false;
        }
    }
    
    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value) {
        try {
            assert redisTemplate != null;
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.warn("置入失败 key: " + key + " cause: ", e);
            return false;
        }
    }
    
    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value, long time) {
        try {
            assert redisTemplate != null;
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.warn("置入失败 key: " + key + " cause: ", e);
            return false;
        }
    }
    
    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void hdel(String key, Object... item) {
        assert redisTemplate != null;
        redisTemplate.opsForHash().delete(key, item);
    }
    
    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键，不能为null
     * @param item 项，不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item) {
        assert redisTemplate != null;
        return redisTemplate.opsForHash().hasKey(key, item);
    }
    
    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return 递增的值
     */
    public static double hincr(String key, String item, double by) {
        assert redisTemplate != null;
        return redisTemplate.opsForHash().increment(key, item, by);
    }
    
    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return 递减的值
     */
    public static double hdecr(String key, String item, double by) {
        assert redisTemplate != null;
        return redisTemplate.opsForHash().increment(key, item, -by);
    }
    // ============================set=============================
    
    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return 获取到的值
     */
    public static Set<Object> sGet(String key) {
        try {
            assert redisTemplate != null;
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.warn("不能获取所有值 key: " + key + " cause: ", e);
            return null;
        }
    }
    
    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sHasKey(String key, Object value) {
        try {
            assert redisTemplate != null;
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
        } catch (Exception e) {
            log.warn("不能在set中查到值是否存在 key: " + key + " cause: ", e);
            return false;
        }
    }
    
    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSet(String key, Object... values) {
        try {
            assert redisTemplate != null;
            Long isSuccess = redisTemplate.opsForSet().add(key, values);
            if (isSuccess == null) {
                return -1L;
            }
            return isSuccess;
        } catch (Exception e) {
            log.warn("不能Set值 key: " + key + " cause: ", e);
            return 0;
        }
    }
    
    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSetAndTime(String key, long time, Object... values) {
        try {
            assert redisTemplate != null;
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            if (count == null) {
                return -1L;
            }
            return count;
        } catch (Exception e) {
            log.warn("不能Set值 key: " + key + " cause: ", e);
            return 0;
        }
    }
    
    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return 缓存的长度
     */
    public static long sGetSetSize(String key) {
        try {
            assert redisTemplate != null;
            Long size = redisTemplate.opsForSet().size(key);
            if (size == null) {
                return -1L;
            }
            return size;
        } catch (Exception e) {
            log.warn("获取不到Set的长度 key: " + key + " cause: ", e);
            return 0;
        }
    }
    
    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long setRemove(String key, Object... values) {
        try {
            assert redisTemplate != null;
            Long removed = redisTemplate.opsForSet().remove(key, values);
            if (removed == null) {
                return -1L;
            }
            return removed;
        } catch (Exception e) {
            log.warn("不能移除指定的值 key: " + key + " 值: " + Arrays.toString(values) + " cause: ", e);
            return 0;
        }
    }
    // ===============================list=================================
    
    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束， [0, 1]
     * @return 获取的缓存的内容
     */
    public static List<Object> lGet(String key, long start, long end) {
        try {
            assert redisTemplate != null;
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.warn("获取不到List的内容 key: " + key + "start index: " + start + ", end index: " + end + " cause: ",
                    e);
            return null;
        }
    }
    
    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return 缓存的长度
     */
    public static long lGetListSize(String key) {
        try {
            assert redisTemplate != null;
            Long size = redisTemplate.opsForList().size(key);
            if (size == null) {
                return -1L;
            }
            return size;
        } catch (Exception e) {
            log.warn("获取不到List的长度 key: " + key + " cause: ", e);
            return 0;
        }
    }
    
    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return 获取到的值
     */
    public static Object lGetIndex(String key, long index) {
        try {
            assert redisTemplate != null;
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.warn("不能通过索引 key: " + key + "获取到集合中的值" + " cause: ", e);
            return null;
        }
    }
    
    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return 置入是否成功
     */
    public static boolean lSet(String key, Object value) {
        try {
            assert redisTemplate != null;
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.warn("指定的值置入不了List: " + value.toString() + "cause: ", e);
            return false;
        }
    }
    
    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return 置入是否成功
     */
    public static boolean lSet(String key, Object value, long time) {
        try {
            assert redisTemplate != null;
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.warn("指定的值置入不了List: " + value.toString() + "cause: ", e);
            return false;
        }
    }
    
    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return 置入是否成功
     */
    public static boolean lSet(String key, List<Object> value) {
        try {
            assert redisTemplate != null;
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.warn("指定的值置入不了List: " + value.toString() + "cause: ", e);
            return false;
        }
    }
    
    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return 置入是否成功
     */
    public static boolean lSet(String key, List<Object> value, long time) {
        try {
            assert redisTemplate != null;
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.warn("指定的值置入不了List: " + value.toString() + "cause: ", e);
            return false;
        }
    }
    
    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return 修改是否成功
     */
    public static boolean lUpdateIndex(String key, long index, Object value) {
        try {
            assert redisTemplate != null;
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.warn("更新不了指定的值: " + value.toString() + "cause: ", e);
            return false;
        }
    }
    
    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static long lRemove(String key, long count, Object value) {
        try {
            assert redisTemplate != null;
            Long removed = redisTemplate.opsForList().remove(key, count, value);
            if (removed == null) {
                return -1L;
            }
            return removed;
        } catch (Exception e) {
            log.warn("删除不了指定的值: " + value.toString() + "cause: ", e);
            return 0;
        }
    }
    
    private static class RedisDecreasingFactorException extends MangoApplicationException {
        
        /**
         * 默认构造器
         */
        RedisDecreasingFactorException() {
        }
        
        /**
         * 带消息的异常信息
         *
         * @param message 消息
         */
        RedisDecreasingFactorException(String message) {
            super(message);
        }
        
        /**
         * 带消息和异常原因的异常信息
         *
         * @param message 消息
         * @param cause   异常原因
         */
        RedisDecreasingFactorException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
