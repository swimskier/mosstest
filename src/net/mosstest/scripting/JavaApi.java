package net.mosstest.scripting;

import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSequentialList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.Deque;
import java.util.Dictionary;
import java.util.EmptyStackException;
import java.util.EnumMap;
import java.util.EventListener;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NavigableMap;
import java.util.Properties;
import java.util.Queue;
import java.util.Random;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.mosstest.sandbox.SandboxClass;
import net.mosstest.sandbox.lang.Runtime;
import net.mosstest.sandbox.lang.System;
import net.mosstest.sandbox.lang.Thread;
import net.mosstest.sandbox.lang.ThreadGroup;
import net.mosstest.sandbox.lang.ThreadLocal;
import net.mosstest.sandbox.util.Enumeration;
import net.mosstest.sandbox.util.ResourceBundle;
import net.mosstest.sandbox.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Class for scripts to instantiate and access the static methods of various approved classes.
 * @author rarkenin
 *
 */
public class JavaApi {
	
	private static final HashMap<String, SandboxClass> qualifiedClasses=new HashMap<String, SandboxClass>(){{
		put("java.lang.Object", new SandboxClass<Object>(java.lang.Object.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.String", new SandboxClass<String>(java.lang.String.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Comparable", new SandboxClass<Comparable>(java.lang.Comparable.class, true, true, false, true)); //$NON-NLS-1$
		put("java.lang.CharSequence", new SandboxClass<CharSequence>(java.lang.CharSequence.class, true, true, false, true)); //$NON-NLS-1$
		put("java.lang.Class", new SandboxClass<Class>(java.lang.Class.class, false, false, true, false)); //$NON-NLS-1$
		put("java.lang.Cloneable", new SandboxClass<Cloneable>(java.lang.Cloneable.class, true, true, false, true)); //$NON-NLS-1$
		put("java.lang.ClassLoader", new SandboxClass<ClassLoader>(java.lang.ClassLoader.class, false, false, true, false)); //$NON-NLS-1$
		put("java.lang.System", new SandboxClass<System>(net.mosstest.sandbox.lang.System.class, true, false, true, false)); //$NON-NLS-1$
		put("java.lang.Throwable", new SandboxClass<Throwable>(java.lang.Throwable.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Error", new SandboxClass<Error>(java.lang.Error.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.ThreadDeath", new SandboxClass<ThreadDeath>(java.lang.ThreadDeath.class, false, false, true, false)); //$NON-NLS-1$
		put("java.lang.Exception", new SandboxClass<Exception>(java.lang.Exception.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.RuntimeException", new SandboxClass<RuntimeException>(java.lang.RuntimeException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.ClassCastException", new SandboxClass<ClassCastException>(java.lang.ClassCastException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.ArrayStoreException", new SandboxClass<ArrayStoreException>(java.lang.ArrayStoreException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.ref.Reference", new SandboxClass<Reference>(java.lang.ref.Reference.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.ref.SoftReference", new SandboxClass<SoftReference>(java.lang.ref.SoftReference.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.ref.WeakReference", new SandboxClass<WeakReference>(java.lang.ref.WeakReference.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.ref.PhantomReference", new SandboxClass<PhantomReference>(java.lang.ref.PhantomReference.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Thread", new SandboxClass<Thread>(net.mosstest.sandbox.lang.Thread.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Runnable", new SandboxClass<Runnable>(java.lang.Runnable.class, true, true, false, true)); //$NON-NLS-1$
		put("java.lang.ref.SoftReference", new SandboxClass<ThreadGroup>(net.mosstest.sandbox.lang.ThreadGroup.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Properties", new SandboxClass<Properties>(java.util.Properties.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Hashtable", new SandboxClass<Hashtable>(java.util.Hashtable.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Map", new SandboxClass<Map>(java.util.Map.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Dictionary", new SandboxClass<Dictionary>(java.util.Dictionary.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Vector", new SandboxClass<Vector>(java.util.Vector.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.List", new SandboxClass<List>(java.util.List.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Collection", new SandboxClass<Collection>(java.util.Collection.class, true, true, false, true)); //$NON-NLS-1$
		put("java.lang.Iterable", new SandboxClass<Iterable>(java.lang.Iterable.class, true, true, false, true)); //$NON-NLS-1$
		put("java.util.RandomAccess", new SandboxClass<RandomAccess>(java.util.RandomAccess.class, true, true, false, true)); //$NON-NLS-1$
		put("java.util.AbstractList", new SandboxClass<AbstractList>(java.util.AbstractList.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.AbstractCollection", new SandboxClass<AbstractCollection>(java.util.AbstractCollection.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.StringBuffer", new SandboxClass<StringBuffer>(java.lang.StringBuffer.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Appendable", new SandboxClass<Appendable>(java.lang.Appendable.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.StackTraceElement", new SandboxClass<StackTraceElement>(java.lang.StackTraceElement.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Boolean", new SandboxClass<Boolean>(java.lang.Boolean.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Character", new SandboxClass<Character>(java.lang.Character.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Float", new SandboxClass<Float>(java.lang.Float.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Number", new SandboxClass<Number>(java.lang.Number.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Double", new SandboxClass<Double>(java.lang.Double.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Byte", new SandboxClass<Byte>(java.lang.Byte.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Short", new SandboxClass<Short>(java.lang.Short.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Integer", new SandboxClass<Integer>(java.lang.Integer.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Long", new SandboxClass<Long>(java.lang.Long.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.NullPointerException", new SandboxClass<NullPointerException>(java.lang.NullPointerException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.ArithmeticException", new SandboxClass<ArithmeticException>(java.lang.ArithmeticException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Comparator", new SandboxClass<Comparator>(java.util.Comparator.class, true, true, false, true)); //$NON-NLS-1$
		put("java.util.AbstractMap", new SandboxClass<AbstractMap>(java.util.AbstractMap.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.HashMap", new SandboxClass<HashMap>(java.util.HashMap.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Stack", new SandboxClass<Stack>(java.util.Stack.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Enumeration", new SandboxClass<Enumeration>(net.mosstest.sandbox.util.Enumeration.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Iterator", new SandboxClass<Iterator>(java.util.Iterator.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.concurrent.atomic.AtomicReferenceFieldUpdater", new SandboxClass<AtomicReferenceFieldUpdater>(net.mosstest.sandbox.util.concurrent.atomic.AtomicReferenceFieldUpdater.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.NoSuchMethodError", new SandboxClass<NoSuchMethodError>(java.lang.NoSuchMethodError.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Collections", new SandboxClass<Collections>(java.util.Collections.class, true, false, false, false)); //$NON-NLS-1$
		put("java.util.AbstractSet", new SandboxClass<AbstractSet>(java.util.AbstractSet.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Set", new SandboxClass<Set>(java.util.Set.class, true, true, false, true)); //$NON-NLS-1$
		put("java.lang.ThreadLocal", new SandboxClass<ThreadLocal>(net.mosstest.sandbox.lang.ThreadLocal.class, false, false, false, false)); //$NON-NLS-1$
		put("java.util.concurrent.atomic.AtomicInteger", new SandboxClass<AtomicInteger>(java.util.concurrent.atomic.AtomicInteger.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Arrays", new SandboxClass<Arrays>(java.util.Arrays.class, true, false, false, false)); //$NON-NLS-1$
		put("java.lang.Math", new SandboxClass<Math>(java.lang.Math.class, true, false, false, false)); //$NON-NLS-1$
		put("java.lang.StringBuilder", new SandboxClass<StringBuilder>(java.lang.StringBuilder.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Runtime", new SandboxClass<Runtime>(net.mosstest.sandbox.lang.Runtime.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Readable", new SandboxClass<Readable>(java.lang.Readable.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.LinkedHashMap", new SandboxClass<LinkedHashMap>(java.util.LinkedHashMap.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.ArrayList", new SandboxClass<ArrayList>(java.util.ArrayList.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.IdentityHashMap", new SandboxClass<IdentityHashMap>(java.util.IdentityHashMap.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.StringTokenizer", new SandboxClass<StringTokenizer>(java.util.StringTokenizer.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.concurrent.ConcurrentHashMap", new SandboxClass<ConcurrentHashMap>(java.util.concurrent.ConcurrentHashMap.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.concurrent", new SandboxClass<ConcurrentMap>(java.util.concurrent.ConcurrentMap.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.concurrent.locks.ReentrantLock", new SandboxClass<ReentrantLock>(java.util.concurrent.locks.ReentrantLock.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.concurrent.locks.Lock", new SandboxClass<Lock>(java.util.concurrent.locks.Lock.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.concurrent.locks.AbstractQueuedSynchronizer", new SandboxClass<AbstractQueuedSynchronizer>(java.util.concurrent.locks.AbstractQueuedSynchronizer.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.concurrent.locks.AbstractOwnableSynchronizer", new SandboxClass<AbstractOwnableSynchronizer>(java.util.concurrent.locks.AbstractOwnableSynchronizer.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.BitSet", new SandboxClass<BitSet>(java.util.BitSet.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.HashSet", new SandboxClass<HashSet>(java.util.HashSet.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.LinkedHashSet", new SandboxClass<LinkedHashSet>(java.util.LinkedHashSet.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.InterruptedException", new SandboxClass<InterruptedException>(java.lang.InterruptedException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.ResourceBundle", new SandboxClass<ResourceBundle>(net.mosstest.sandbox.util.ResourceBundle.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.EventListener", new SandboxClass<EventListener>(java.util.EventListener.class, true, true, false, true)); //$NON-NLS-1$
		put("java.awt.geom.AffineTransform", new SandboxClass<AffineTransform>(java.awt.geom.AffineTransform.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.Enum", new SandboxClass<Enum>(java.lang.Enum.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.WeakHashMap", new SandboxClass<WeakHashMap>(java.util.WeakHashMap.class, true, true, false, false)); //$NON-NLS-1$
		put("java.awt.geom.Dimension2D", new SandboxClass<Dimension2D>(java.awt.geom.Dimension2D.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.concurrent.atomic.AtomicBoolean", new SandboxClass<AtomicBoolean>(java.util.concurrent.atomic.AtomicBoolean.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.NoSuchMethodException", new SandboxClass<NoSuchMethodException>(java.lang.NoSuchMethodException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.awt.geom.Point2D", new SandboxClass<Point2D>(java.awt.geom.Point2D.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.UnsupportedOperationException", new SandboxClass<UnsupportedOperationException>(java.lang.UnsupportedOperationException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.awt.geom.Rectangle2D", new SandboxClass<Rectangle2D>(java.awt.geom.Rectangle2D.class, true, true, false, false)); //$NON-NLS-1$
		put("java.awt.geom.RectangularShape", new SandboxClass<RectangularShape>(java.awt.geom.RectangularShape.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.SecurityException", new SandboxClass<SecurityException>(java.lang.SecurityException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.IllegalStateException", new SandboxClass<IllegalStateException>(java.lang.IllegalStateException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.awt.geom.Path2D", new SandboxClass<Path2D>(java.awt.geom.Path2D.class, true, true, false, false)); //$NON-NLS-1$
		put("java.awt.geom.GeneralPath", new SandboxClass<GeneralPath>(java.awt.geom.GeneralPath.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.MissingResourceException", new SandboxClass<MissingResourceException>(java.util.MissingResourceException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.LinkedList", new SandboxClass<LinkedList>(java.util.LinkedList.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Deque", new SandboxClass<Deque>(java.util.Deque.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Queue", new SandboxClass<Queue>(java.util.Queue.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.AbstractSequentialList", new SandboxClass<AbstractSequentialList>(java.util.AbstractSequentialList.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.EmptyStackException", new SandboxClass<EmptyStackException>(java.util.EmptyStackException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.TreeSet", new SandboxClass<TreeSet>(java.util.TreeSet.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.NavigableMap", new SandboxClass<NavigableMap>(java.util.NavigableMap.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.SortedMap", new SandboxClass<SortedMap>(java.util.SortedMap.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.ListIterator", new SandboxClass<ListIterator>(java.util.ListIterator.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.IllegalAccessException", new SandboxClass<IllegalAccessException>(java.lang.IllegalAccessException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.regex.Pattern", new SandboxClass<Pattern>(java.util.regex.Pattern.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.StrictMath", new SandboxClass<StrictMath>(java.lang.StrictMath.class, true, false, false, false)); //$NON-NLS-1$
		put("java.lang.NumberFormatException", new SandboxClass<NumberFormatException>(java.lang.NumberFormatException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.IllegalArgumentException", new SandboxClass<IllegalArgumentException>(java.lang.IllegalArgumentException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Date", new SandboxClass<Date>(java.util.Date.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.TimeZone", new SandboxClass<TimeZone>(java.util.TimeZone.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.regex.Matcher", new SandboxClass<Matcher>(java.util.regex.Matcher.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.regex.MathResult", new SandboxClass<MatchResult>(java.util.regex.MatchResult.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Random", new SandboxClass<Random>(java.util.Random.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.concurrent.atomic.AtomicLong", new SandboxClass<AtomicLong>(java.util.concurrent.atomic.AtomicLong.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.InternalError", new SandboxClass<InternalError>(java.lang.InternalError.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.NoSuchFieldException", new SandboxClass<NoSuchFieldException>(java.lang.NoSuchFieldException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.InstantiationException", new SandboxClass<InstantiationException>(java.lang.InstantiationException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.ArrayIndexOutOfBoundsException", new SandboxClass<ArrayIndexOutOfBoundsException>(java.lang.ArrayIndexOutOfBoundsException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.IllegalAccessError", new SandboxClass<IllegalAccessError>(java.lang.IllegalAccessError.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.CloneNotSupportedException", new SandboxClass<CloneNotSupportedException>(java.lang.CloneNotSupportedException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.UnsatisfiedLinkError", new SandboxClass<UnsatisfiedLinkError>(java.lang.UnsatisfiedLinkError.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Calendar", new SandboxClass<Calendar>(java.util.Calendar.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.GregorianCalendar", new SandboxClass<GregorianCalendar>(java.util.GregorianCalendar.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.Currency", new SandboxClass<Currency>(java.util.Currency.class, true, true, false, false)); //$NON-NLS-1$
		put("java.math.RoundingMode", new SandboxClass<RoundingMode>(java.math.RoundingMode.class, true, true, false, false)); //$NON-NLS-1$
		put("java.lang.StringIndexOutOfBoundsException", new SandboxClass<StringIndexOutOfBoundsException>(java.lang.StringIndexOutOfBoundsException.class, true, true, false, false)); //$NON-NLS-1$
		put("java.math.BigInteger", new SandboxClass<BigInteger>(java.math.BigInteger.class, true, true, false, false)); //$NON-NLS-1$
		put("java.util.EnumMap", new SandboxClass<EnumMap>(java.util.EnumMap.class, true, true, false, false)); //$NON-NLS-1$
	}};
	
	public static Object getInstance(String clazz, Object... constructorParams) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		SandboxClass<?> sc=qualifiedClasses.get(clazz);
		return sc.getInstance(constructorParams);
	}
	
	public static Object invokeStatic(String clazz, String method,
			Object... parameters) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		SandboxClass<?> sc=qualifiedClasses.get(clazz);
		return sc.invokeStatic(method, parameters);
	}
}
