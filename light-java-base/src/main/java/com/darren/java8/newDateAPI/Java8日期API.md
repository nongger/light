
新API基于ISO标准日历系统，java.time包下的所有类都是不可变类型而且线程安全。

| 编号 | 类的名称 | 描述 |
|----|----|----|
|1  |Instant|-时间戳-|
|2	|Duration|	持续时间，时间差|
|3	|LocalDate|	只包含日期，比如：2018-02-05|
|4	|LocalTime|	只包含时间，比如：23:12:10|
|5	|LocalDateTime|	包含日期和时间，比如：2018-02-05 23:14:21|
|6	|Period|	时间段|
|7	|ZoneOffset|	时区偏移量，比如：+8:00|
|8	|ZonedDateTime|	带时区的时间|
|9	|Clock|	时钟，比如获取目前美国纽约的时间|
|10 |java.time.format.DateTimeFormatter|	时间格式化|
示例1:Java 8中获取今天的日期
