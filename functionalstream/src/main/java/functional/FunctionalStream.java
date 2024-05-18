package functional;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class FunctionalStream {
    static final Consumer PRN = t -> System.out.print(" " + t);
    static final Consumer PRINT = t -> System.out.println("    " + t);
    static final IntConsumer PRN_I = t -> System.out.print(" " + t);
    static final IntConsumer PRINT_INT = t -> System.out.println("    " + t);
    static final DoubleConsumer PRINT_DUB = t -> System.out.println("    " + t);
    public static void main(String[] args) {
        //*
        demo101CreateStreamGenerate();
        demo102CreateStreamRandom();
        demo103CreateStreamIterate();
        demo104CreateStreamRange();
        demo105CreateStreamBuilder();
        demo106CreateStreamOf();
        demo201ProcessStreamLimitSkipDistinct();
        demo202ProcessStreamSorted();
        demo203ProcessStreamFilter();
        demo204ProcessStreamMap();
        demo205ProcessStreamFlatMap();
        demo206ProcessStreamMapMulti();
        demo207ProcessStreamPeek();
        demo301TerminateStreamCountMinMax();
        demo302TerminateStreamSumAverage(); // *********
        demo303TerminateStreamToArray();
        demo304TerminateStreamFindAndOptional();
        demo305TerminateStreamMatch();
        demo306TerminateStreamReduce();
        demo307TerminateStreamCollect();
        demo308TerminateStreamCollector(); // *********
        //*/
    }
    public static void demo101CreateStreamGenerate() {
       System.out.println("101.StreamCreation:Generate");
       //Stream.generate(Supplier<T> s)
       //Stream.generate(() -> Math.random());
       Stream.generate(Math::random)
          .limit(3)
          .forEach(PRINT);
    }
    public static void demo102CreateStreamRandom() {
       System.out.println("102.StreamCreation:Random");
       var random = new Random();
       System.out.println("  Random 4 doubles in range [0.0,10.0)");
       // 4 doubles: starts from 0.0 (inclusive) up to 10.0 (exclusive)
       random.doubles(4,0.0,10.0).forEach(PRINT_DUB);
       System.out.println("  Random 3 ints in range [1,11)");
       // 3 ints: starts from 1 (inclusive) up to 11 (exclusive)
       random.ints(3,1,11).forEach(PRINT_INT);
       System.out.println("  Random 5 doubles");
       random.doubles(5).forEach(PRINT_DUB);
    }
    public static void demo103CreateStreamIterate() {
       System.out.println("103.StreamCreation:Iterate");
       //Stream.iterate(intialization,condition_to_continue,step)
       System.out.println("  iterate for (i=5, i<10, i+=2)");
       IntStream.iterate(5, i -> i < 10, i -> i + 2).forEach(PRINT_INT);
       System.out.println("  iterate for (s=\"A\", s.length()<10, s=s+s)");
       Stream.iterate("A", s -> s.length() < 10, s -> s + s).forEach(PRINT);
    }
    public static void demo104CreateStreamRange() {
       System.out.println("104.StreamCreation:Range");
       // for IntStream only
       System.out.print("  IntStream.range(7,12) :");
       IntStream.range(7, 12).forEach(PRN_I);
       System.out.println();
       System.out.print("  IntStream.rangeClosed(7,12) :");
       IntStream.rangeClosed(7, 12).forEach(PRN_I);
       System.out.println();
    }
    public static void demo105CreateStreamBuilder() {
       System.out.println("105.StreamCreation:Builder");
       Stream.builder().add("ONE").add("TWO").add("THREE").build()
          .forEach(PRINT);
    }
    public static void demo106CreateStreamOf() {
       System.out.println("106.StreamCreation:of/ofNullable/empty");
       System.out.println("  (1) Stream.of(a_single_object)");
       Stream.of("one").forEach(PRINT);
       System.out.println("  (2) Stream.of(vararg_of_objects)");
       Stream.of("one","two","three").forEach(PRINT);
       System.out.println("  (3) Stream.of(vararg_of_ints)");
       Stream.of(1,2,3).forEach(PRINT);
       System.out.println("  (4) IntStream.of(vararg_of_ints)");
       IntStream.of(1,2,3).forEach(PRINT_INT);
       var strList = List.of("one","two");
       System.out.println("  (5) Stream.of(a_List_of_objects)");
       Stream.of(strList).forEach(PRINT);
       System.out.println("  (6) a_List_of_objects.stream()");
       strList.stream().forEach(PRINT);
       var intList = List.of(1,2);
       System.out.println("  (7) Stream.of(a_List_of_ints)");
       Stream.of(intList).forEach(PRINT);
       System.out.println("  (8) a_List_of_ints.stream()");
       intList.stream().forEach(PRINT);
       String[] strArray = {"one","two"};
       System.out.println("  (9) Stream.of(an_array_of_objects)");
       Stream.of(strArray).forEach(PRINT);
       System.out.println("  (10) Arrays.stream(an_array_of_objects)");
       Arrays.stream(strArray).forEach(PRINT);
       int[] intArray = {1,2,3};
       System.out.println("  (11) Stream.of(an_array_of_ints) !!! (just like one object)");
       Stream.of(intArray).forEach(PRINT);
       System.out.println("  (12) IntStream.of(an_array_of_ints)");
       IntStream.of(intArray).forEach(PRINT_INT);
       System.out.println("  (13) Arrays.stream(an_array_of_ints)");
       Arrays.stream(intArray).forEach(PRINT_INT);
       System.out.println("  (14) Stream.ofNullable(null)");
       Stream.ofNullable(null).forEach(PRINT);
       System.out.println("  (15) Stream.empty()");
       Stream.empty().forEach(PRINT);
       System.out.println("  (16) Stream.ofNullable(nonNull)");
       System.out.print("       anObject:       ");
       Stream.ofNullable("one").forEach(PRINT);
       System.out.print("       anInt:          ");
       Stream.ofNullable(1).forEach(PRINT);
       System.out.print("       anObjectArray: *");
       Stream.ofNullable(strArray).forEach(PRINT);
       System.out.print("       anIntArray:    *");
       Stream.ofNullable(intArray).forEach(PRINT);
       System.out.print("       anObjectList:   ");
       Stream.ofNullable(strList).forEach(PRINT);
       System.out.print("       anIntList:      ");
       Stream.ofNullable(intList).forEach(PRINT);
    }
    public static void demo201ProcessStreamLimitSkipDistinct() {
       System.out.println("201.StreamNonTerminal:limit,skip,distinct");
       var strList = List.of("one","two","three","one","four","five","four","six");
       strList.stream()
          .skip(2)
          .limit(5)
          .distinct()
          .forEach(PRINT);
    }
    public static void demo202ProcessStreamSorted() {
       System.out.println("202.StreamNonTerminal:sorted");
       var strList = List.of("one","Two","three","Four","five","six");
       System.out.print("    sorted: ");
       strList.stream().sorted().forEach(PRN);
       System.out.println();
       System.out.print("    sorted(comparator): ");
       strList.stream().sorted(String.CASE_INSENSITIVE_ORDER).forEach(PRN);
       System.out.println();
    }
    public static void demo203ProcessStreamFilter() {
       System.out.println("203.StreamNonTerminal:filter");
       var strList = List.of("one","Two","three","Four","five","six");
       System.out.print("   ");
       strList.stream().forEach(PRN); System.out.println();
       System.out.print("   ");
       strList.stream()
          .filter(s->s.contains("e"))
          .forEach(PRN);
       System.out.println();
    }
    public static void demo204ProcessStreamMap() {
       System.out.println("204.StreamNonTerminal:map");
       var strList = List.of("one","Two","three","Four","five","six");
       System.out.print("   ");
       strList.stream().forEach(PRN); System.out.println();
       System.out.println("    (1) .stream().map(Function<T,R> func)");
       System.out.print("       ");
       strList.stream()
          .map(s->s.substring(1, s.length()-1))
          .forEach(PRN);
       System.out.println();
       System.out.println("    (2) .stream().mapToInt(ToIntFunction<T> func)");
       System.out.print("       ");
       strList.stream()
          .mapToInt(s->s.length())
          .forEach(PRN_I);
       System.out.println();
    }
    public static void demo205ProcessStreamFlatMap() {
       System.out.println("205.StreamNonTerminal:flatMap");
       // flatMap changes a Stream of structures (Collection/Array)
       // to a Stream of elements inside the structures
       List<List<String>> strListList = List.of(
          List.of("one","Two"),
          List.of("three","Four","five"),
          List.of("six"));
       System.out.println("    list of list: " + strListList);
       System.out.print("    stream of list:");
       strListList       // = List<List<String>>
          .stream()      // = Stream<List<String>>
          .forEach(PRN);
       System.out.println();
       System.out.print("    map:");
       strListList           // = List<List<String>>
          .stream()          // = Stream<List<String>>
          .map(List::stream) // = Stream<Stream<String>>
          .forEach(stream -> stream.forEach(PRN));
       System.out.println();
       System.out.print("    flatMap:");
       strListList               // = List<List<String>>
          .stream()              // = Stream<List<String>>
          .flatMap(List::stream) // = Stream<String>
          .forEach(PRN);
       System.out.println();
       System.out.print("    firstTwo:");
       strListList                   // = List<List<String>>
          .stream()                  // = Stream<List<String>>
          .flatMap(List::stream)     // = Stream<String>
          .map(e->e.substring(0, 2))
          .forEach(PRN);
       System.out.println();
    }
    public static void demo206ProcessStreamMapMulti() {
       System.out.println("206.StreamNonTerminal:mapMulti");
       // mapMulti map each element to zero-or-more elements
       
       List<String> strList = List.of(
          "programmer", "application developer", "software testing engineer");
       System.out.println("    list: " + strList);

       System.out.print("    stream:");
       strList
          .stream()
          .map(s -> '(' + s + ')')
          .forEach(PRN);
       System.out.println();

       System.out.print("    mapMulti:");
       strList
          .stream()
          .mapMulti((str, mapper) -> {
             // choose only Strings containing whitespace
             if (str.matches(".*\\s+.*")) { // work as a filter
                // split each String into words
                for (var word : str.split("\\s+")) { // work as a flatMap
                   // then wrap each word with () 
                   // and send it out to the stream
                   mapper.accept('(' + word + ')'); // work as a map
                }
             }
          })
          .forEach(PRN);
       System.out.println();
       
       System.out.print("    filter+flatMap+map:");
       strList
          .stream()
          .filter(str -> str.matches(".*\\s+.*"))
          .flatMap(str -> Stream.of(str.split("\\s+")))
          .map(w->'(' + w + ')')
          .forEach(PRN);
       System.out.println();
    }
    public static void demo207ProcessStreamPeek() {
       System.out.println("207.StreamProcess:peek");
       System.out.println("  *** example 1 ***");
       Stream.iterate(1,a->a<=64,a->++a).toList().parallelStream()
          .map(s->" "+s)
          .peek(x->System.out.println(
             "    [" + x + " (thread:" + Thread.currentThread().getId() + ")]"))
          .forEach(PRINT);

       System.out.println("  *** example 2 ***");
       List<String> peeker = Collections.synchronizedList(new LinkedList<>());
       Stream.iterate(1,a->a<=64,a->++a).toList().parallelStream()
          .map(s->" "+s)
          .peek(x ->
             peeker.add("   [" + x + " (thread:" + Thread.currentThread().getId() + ")]"))
          .forEach(PRN);
       System.out.println("\n  Peeker:");
       peeker.forEach(PRINT);
    }
    public static void demo301TerminateStreamCountMinMax() {
       System.out.println("301.StreamTerminal:count,min,max");
       var strList = List.of("one","Two","three","Four","five","six");
       strList.stream().forEach(PRN);
       System.out.println();
       System.out.println("  count=" + strList.stream().count());
       System.out.print("  min=");
       strList.stream()
          .min(Comparator.naturalOrder())
          .ifPresentOrElse(System.out::println,
             () -> System.out.println("NO ANSWER"));
       System.out.print("  max=");
       strList.stream()
          .max(Comparator.naturalOrder())
          .ifPresentOrElse(System.out::println,
             () -> System.out.println("NO ANSWER"));
    }
    public static void demo302TerminateStreamSumAverage() {
       System.out.println("302.StreamTerminal:sum,average");       
    }
    public static void demo303TerminateStreamToArray() {
       System.out.println("303.StreamTerminal:toArray,toList");
       var strList = List.of("one","Two","three","Four","five","six");
       System.out.print("  toList():");
       List<String> ls = strList.stream().map(String::toUpperCase).toList();
       for (var s : ls) System.out.print(" " + s.substring(1, s.length()));
       System.out.println();
       System.out.print("  toArray():");
       Object[] oar = strList.stream().map(String::toLowerCase).toArray();
       for (var o : oar) System.out.print(" " + ((String) o).substring(0,3));
       System.out.println();
       System.out.print("  toArray(arrayContructor):");
       String[] sar = strList.stream().toArray(String[]::new);
       for (var s : sar) System.out.print(" " + s.substring(0,2).toUpperCase());
       System.out.println();
    }
    public static void demo304TerminateStreamFindAndOptional() {
       System.out.println("304.StreamTerminal:findFirst.findAny.Optional");
       var strList = List.of("one","Two","three","Four","five","six");
       List<String> emptyList = List.of();
       System.out.print("  findFirst: ");
       var f = strList.stream().findFirst().orElse("NOTFOUND");
       System.out.println(f);
       System.out.print("  findFirst (on empty) with orElse(defaultString): ");
       f = emptyList.stream().findFirst()
          .orElse("NOTFOUND");
       System.out.println(f);
       System.out.print("  findFirst (on empty) with orElseGet(defaultStringSupplier): ");
       f = emptyList.stream().findFirst()
          .orElseGet(()->"NOTFOUND");
       System.out.println(f);
       System.out.print("  findFirst (on empty) with or(optionalStringSupploer): ");
       Optional<String> o = emptyList.stream().findFirst()
          .or(() -> Optional.of("NOTFOUND"));
       System.out.println(o.orElseThrow());
    }
    public static void demo305TerminateStreamMatch() {
       System.out.println("305.StreamTerminal:allMatch,anyMatch,noneMatch");
       var strList = List.of("one","Two","three","Four","five","six");
       List<String> emptyList = List.of();
       Predicate<String> oneUp = s -> s.length() >= 1;
       Predicate<String> threeUp = s -> s.length() >= 3;
       Predicate<String> tenUp = s -> s.length() >= 10;
       System.out.print("  Stream of :");
       strList.stream().forEach(PRN);
       System.out.println();
       System.out.println("    some oneUp: " + strList.stream().anyMatch(oneUp));
       System.out.println("    some threeUp: " + strList.stream().anyMatch(threeUp));
       System.out.println("    some tenUp: " + strList.stream().anyMatch(tenUp));
       System.out.println("    all oneUp: " + strList.stream().allMatch(oneUp));
       System.out.println("    all threeUp: " + strList.stream().allMatch(threeUp));
       System.out.println("    all tenUp: " + strList.stream().allMatch(tenUp));
       System.out.println("    none oneUp: " + strList.stream().noneMatch(oneUp));
       System.out.println("    none threeUp: " + strList.stream().noneMatch(threeUp));
       System.out.println("    none tenUp: " + strList.stream().noneMatch(tenUp));
       System.out.println("  On empty stream :");
       System.out.println("  'for some on empty set' is always false");
       System.out.println("    some oneUp: " + emptyList.stream().anyMatch(oneUp));
       System.out.println("    some threeUp: " + emptyList.stream().anyMatch(threeUp));
       System.out.println("    some tenUp: " + emptyList.stream().anyMatch(tenUp));
       System.out.println("  'for all on empty set' is always true");
       System.out.println("    all oneUp: " + emptyList.stream().allMatch(oneUp));
       System.out.println("    all threeUp: " + emptyList.stream().allMatch(threeUp));
       System.out.println("    all tenUp: " + emptyList.stream().allMatch(tenUp));
       System.out.println("  'for none on empty set' is always true");
       System.out.println("    none oneUp: " + emptyList.stream().noneMatch(oneUp));
       System.out.println("    none threeUp: " + emptyList.stream().noneMatch(threeUp));
       System.out.println("    none tenUp: " + emptyList.stream().noneMatch(tenUp));
    }
    public static void demo306TerminateStreamReduce() {
       System.out.println("306.StreamTerminal:reduce");
       var strList = List.of("Zero","Two","Four","Six","Eight");
       var ints = new int[] {3,5,7};
       System.out.println("  (1) reduce with accumulator [concatenation/summation]");
       // [a1,a2,a3,...].reduce(op(e1,e2))
       // on an empty stream [ ]
       //    result = an optional empty
       // on a stream of a single element [ a1 ]
       //    result = an optional of a1
       // on a sequential stream of multiple elements [ a1, a2, a3, ... ]
       //    result = an optional of op(op(op(a1,a2),a3),a4)...
       // on a parallel stream of multiple elements [ a1, a2, a3, ... ]
       //    t1 = op(...op(op(a1,a2),a3),...) on stream 1
       //    t2 = op(...op(op(a11,a12),a13),...) on stream 2
       //    tn = ... on stream n
       //    result = an optional of op(...op(op(t1,t2),t3)...,tn)
       var OptionalStr = strList.stream().reduce((s,t) -> s + t);
       System.out.print("    concat of");
       strList.forEach(PRN);
       System.out.println(" = " + OptionalStr.orElse("NOTHING"));
       var OptionalSum = Arrays.stream(ints).reduce((s,t) -> s + t);
       System.out.print("    summation of");
       Arrays.stream(ints).forEach(PRN_I);
       System.out.println(" = " + OptionalSum.orElse(0));
       //----------------------------------------
       System.out.println("  (2) reduce with identity and accumulator");
       // [a1,a2,a3,...].reduce(id,op(e1,e2))
       // on an empty stream
       //    result = id
       // on a stream of a single element
       //    result = op(id,a1)
       // on a sequential stream of multiple elements
       //    result = op(...op(op(op(id,a1),a2),a3),...)
       // on parallel streams of multiple elements
       //    t1 = op(...op(op(id,a1),a2),...) on stream 1
       //    t2 = op(...op(op(id,a11),a12),...) on stream 2
       //    tn = ... on stream n
       //    result = op(...op(op(t1,t2),t3),...,tn)
       var initialStr = strList.stream()
          .reduce("",(acc,each) -> acc + each.substring(0,1).toUpperCase());
       System.out.print("    concat of initial of");
       strList.forEach(PRN);
       System.out.println(" = " + initialStr);
       var sumSqr = Arrays.stream(ints)
          .reduce(0,(acc,each) -> acc + each * each);
       System.out.print("    sum square of");
       Arrays.stream(ints).forEach(PRN_I);
       System.out.println(" = " + sumSqr);
       //----------------------------------------
       System.out.println("  (3) reduce with identity, accumulator, and combiner for parallelism");
       // [a1,a2,a3,...].reduce(id,op(e1,e2),cb(s1,s2))
       // on an empty stream
       //    result = id
       // on a stream of a single element
       //    result = op(id,a1)
       // on a sequential stream of multiple elements
       //    result = op(op(op(op(id,a1),a2),a3),...)
       // on parallel streams of multiple elements
       //    t1 = op(...op(op(id,a1),a2),...) on stream 1
       //    t2 = op(...op(op(id,a11),a12),...) on stream 2
       //    tn = ...
       //    result = cb(...cb(cb(t1,t2),t3),...,tn)
       initialStr = strList.stream().unordered().parallel()
          .reduce("" /* identity */,
             (acc,each) -> acc + each.substring(0,1).toUpperCase() /* accumulating */,
             (acc1, acc2) -> acc1 + acc2 /* combining */);
       System.out.print("    concat of initial of");
       strList.forEach(PRN);
       System.out.println(" = " + initialStr);
       sumSqr = Arrays.stream(ints).boxed().parallel()
          .reduce(0 /* identity */,
             (acc,each) -> acc + each * each /* accumulating process */,
             (acc1, acc2) -> acc1 + acc2 /* combining process */);
       System.out.print("    sum square of");
       Arrays.stream(ints).forEach(PRN_I);
       System.out.println(" = " + sumSqr);
    }
    public static void demo307TerminateStreamCollect() {
       System.out.println("307.StreamTerminal:collect");
       var strList = List.of("Zero","Two","Four","Six","Eight");
       System.out.print("  Stream of :");
       strList.stream().forEach(PRN);
       System.out.println();
       Map<String,Integer> result = strList.stream().parallel()
          .collect(
             TreeMap<String,Integer>::new /* supplier */
             /* for supplying mutable accumulators for storing results */,
             (acc,each) -> acc.put(each,each.length()) /* accumulating process */
             /* for accumulating each element into the accumulator */,
             (acc1, acc2) -> acc1.putAll(acc2) /* combining process */
             /* for combining all accumulators into one accumulator */
          );
       for (var e : result.entrySet()) {
          System.out.format("    Element: %-8s has length: %d\n",
             e.getKey(), e.getValue());
       }
    }
    public static void demo308TerminateStreamCollector() {
       System.out.println("308.StreamTerminal:collector");
    }
}