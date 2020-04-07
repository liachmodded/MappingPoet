package net.fabricmc.mappingpoet;

import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SignaturesTest {

	@Test
	public void testRandomMapType() {
		//signature Ljava/util/Map<Ljava/util/Map$Entry<[[Ljava/lang/String;[Ljava/util/List<[I>;>;[[[D>;
		//Map<Map.Entry<String[][], List<int[]>[]>, double[][][]> map = new HashMap<>();
		String signature = "Ljava/util/Map<Ljava/util/Map$Entry<[[Ljava/lang/String;[Ljava/util/List<[I>;>;[[[D>;";
		Map.Entry<Integer, TypeName> result = Signatures.parseParameterizedType(signature, 0);

		Assertions.assertEquals(85, result.getKey().intValue());
		Assertions.assertEquals("java.util.Map<java.util.Map.Entry<java.lang.String[][], java.util.List<int[]>[]>, double[][][]>", result.getValue().toString());
	}

	@Test
	public void testCrazyOne() {
		String classSignature = "<B::Ljava/util/Comparator<-TA;>;C:Ljava/lang/ClassLoader;:Ljava/lang/Iterable<*>;>Ljava/lang/Object;";
		Map.Entry<Integer, TypeName> result = Signatures.parseParameterizedType(classSignature, 4);
		Assertions.assertEquals(32, result.getKey().intValue());
		Assertions.assertEquals("java.util.Comparator<? super A>", result.getValue().toString());

		result = Signatures.parseParameterizedType(classSignature, 34);
		Assertions.assertEquals(57, result.getKey().intValue());
		Assertions.assertEquals("java.lang.ClassLoader", result.getValue().toString());

		result = Signatures.parseParameterizedType(classSignature, 58);
		Assertions.assertEquals(81, result.getKey().intValue());
		Assertions.assertEquals("java.lang.Iterable<?>", result.getValue().toString());
	}

	@Test
	public void soo() {
		TestOuter<Integer>.Inner<Comparator<Integer>, URLClassLoader>.ExtraInner<UnaryOperator<Map<int[][], BiFunction<Comparator<Integer>, Integer, URLClassLoader>>>> local = new TestOuter<Integer>().new Inner<Comparator<Integer>, URLClassLoader>().new ExtraInner<UnaryOperator<Map<int[][], BiFunction<Comparator<Integer>, Integer, URLClassLoader>>>>();
		local.hashCode();

		// signature Lnet/fabricmc/mappingpoet/TestOuter<Ljava/lang/Integer;>.Inner<Ljava/util/Comparator<Ljava/lang/Integer;>;Ljava/net/URLClassLoader;>.ExtraInner<Ljava/util/function/UnaryOperator<Ljava/util/Map<[[ILjava/util/function/BiFunction<Ljava/util/Comparator<Ljava/lang/Integer;>;Ljava/lang/Integer;Ljava/net/URLClassLoader;>;>;>;>;
		String signature = "Lnet/fabricmc/mappingpoet/TestOuter<Ljava/lang/Integer;>.Inner<Ljava/util/Comparator<Ljava/lang/Integer;>;Ljava/net/URLClassLoader;>.ExtraInner<Ljava/util/function/UnaryOperator<Ljava/util/Map<[[ILjava/util/function/BiFunction<Ljava/util/Comparator<Ljava/lang/Integer;>;Ljava/lang/Integer;Ljava/net/URLClassLoader;>;>;>;>;";
		Map.Entry<Integer, TypeName> result = Signatures.parseParameterizedType(signature, 0);

		Assertions.assertEquals(322, result.getKey().intValue());
		Assertions.assertEquals("net.fabricmc.mappingpoet.TestOuter<java.lang.Integer>.Inner<java.util.Comparator<java.lang.Integer>, java.net.URLClassLoader>.ExtraInner<java.util.function.UnaryOperator<java.util.Map<int[][], java.util.function.BiFunction<java.util.Comparator<java.lang.Integer>, java.lang.Integer, java.net.URLClassLoader>>>>", result.getValue().toString());
	}

	@Test
	public void arrSoo() {
		@SuppressWarnings("unchecked")
		TestOuter<Integer>.Inner<Comparator<Integer>, URLClassLoader>.ExtraInner<UnaryOperator<Map<int[][], BiFunction<Comparator<Integer>, Integer, URLClassLoader>>>>[][] arr = (TestOuter<Integer>.Inner<Comparator<Integer>, URLClassLoader>.ExtraInner<UnaryOperator<Map<int[][], BiFunction<Comparator<Integer>, Integer, URLClassLoader>>>>[][]) new TestOuter<?>.Inner<?, ?>.ExtraInner<?>[0][];
		arr.toString();
		// signature [[Lnet/fabricmc/mappingpoet/TestOuter<Ljava/lang/Integer;>.Inner<Ljava/util/Comparator<Ljava/lang/Integer;>;Ljava/net/URLClassLoader;>.ExtraInner<Ljava/util/function/UnaryOperator<Ljava/util/Map<[[ILjava/util/function/BiFunction<Ljava/util/Comparator<Ljava/lang/Integer;>;Ljava/lang/Integer;Ljava/net/URLClassLoader;>;>;>;>;
		String arraySignature = "[[Lnet/fabricmc/mappingpoet/TestOuter<Ljava/lang/Integer;>.Inner<Ljava/util/Comparator<Ljava/lang/Integer;>;Ljava/net/URLClassLoader;>.ExtraInner<Ljava/util/function/UnaryOperator<Ljava/util/Map<[[ILjava/util/function/BiFunction<Ljava/util/Comparator<Ljava/lang/Integer;>;Ljava/lang/Integer;Ljava/net/URLClassLoader;>;>;>;>;";
		Map.Entry<Integer, TypeName> result = Signatures.parseParameterizedType(arraySignature, 0);

		Assertions.assertEquals(324, result.getKey().intValue());
		Assertions.assertEquals("net.fabricmc.mappingpoet.TestOuter<java.lang.Integer>.Inner<java.util.Comparator<java.lang.Integer>, java.net.URLClassLoader>.ExtraInner<java.util.function.UnaryOperator<java.util.Map<int[][], java.util.function.BiFunction<java.util.Comparator<java.lang.Integer>, java.lang.Integer, java.net.URLClassLoader>>>>[][]", result.getValue().toString());
	}

	@Test
	public void testClassDeeSignature() {
		// signature <D::Ljava/util/function/UnaryOperator<Ljava/util/Map<[[ILjava/util/function/BiFunction<TB;TA;TC;>;>;>;>Ljava/lang/Object;
		String classSig = "<D::Ljava/util/function/UnaryOperator<Ljava/util/Map<[[ILjava/util/function/BiFunction<TB;TA;TC;>;>;>;>Ljava/lang/Object;";
		Map.Entry<Integer, TypeName> dBound = Signatures.parseParameterizedType(classSig, 4);

		Assertions.assertEquals(102, dBound.getKey().intValue());
		Assertions.assertEquals("java.util.function.UnaryOperator<java.util.Map<int[][], java.util.function.BiFunction<B, A, C>>>", dBound.getValue().toString());

		Signatures.ClassSignature parsed = Signatures.parseClassSignature(classSig);
		Assertions.assertIterableEquals(Collections.singleton(TypeVariableName.get("D")), parsed.generics);
		Assertions.assertEquals(ClassName.OBJECT, parsed.superclass);
		Assertions.assertIterableEquals(Collections.emptyList(), parsed.superinterfaces);
	}

	@Test
	public void testCollectionIntFunctionToArraySignature() {
		// signature <T:Ljava/lang/Object;>(Ljava/util/function/IntFunction<[TT;>;)[TT;
		String methodSignature = "<T:Ljava/lang/Object;>(Ljava/util/function/IntFunction<[TT;>;)[TT;";
		Signatures.MethodSignature parsed = Signatures.parseMethodSignature(methodSignature);
		Assertions.assertIterableEquals(Collections.singleton(TypeVariableName.get("T")), parsed.generics);
		Assertions.assertEquals(ArrayTypeName.of(TypeVariableName.get("T")), parsed.result);
		Assertions.assertIterableEquals(Collections.emptyList(), parsed.thrown);
		Assertions.assertIterableEquals(Collections.singleton(ParameterizedTypeName.get(ClassName.get(IntFunction.class), ArrayTypeName.of(TypeVariableName.get("T")))), parsed.parameters);
	}

	@Test
	public void testCollectionArrayToArraySignature() {
		// signature <T:Ljava/lang/Object;>([TT;)[TT;
		String methodSignature = "<T:Ljava/lang/Object;>([TT;)[TT;";
		Signatures.MethodSignature parsed = Signatures.parseMethodSignature(methodSignature);
		Assertions.assertIterableEquals(Collections.singleton(TypeVariableName.get("T")), parsed.generics);
		Assertions.assertEquals(ArrayTypeName.of(TypeVariableName.get("T")), parsed.result);
		Assertions.assertIterableEquals(Collections.emptyList(), parsed.thrown);
		Assertions.assertIterableEquals(Collections.singleton(ArrayTypeName.of(TypeVariableName.get("T"))), parsed.parameters);
	}

	@Test
	public void testTweakLastSignature() {
		// signature (Ljava/util/function/UnaryOperator<Lcom/squareup/javapoet/TypeName;>;)V
		String methodSignature = "(Ljava/util/function/UnaryOperator<Lcom/squareup/javapoet/TypeName;>;)V";
		Signatures.MethodSignature parsed = Signatures.parseMethodSignature(methodSignature);
		Assertions.assertNull(parsed.generics);
		Assertions.assertEquals(TypeName.VOID, parsed.result);
		Assertions.assertIterableEquals(Collections.emptyList(), parsed.thrown);
		ClassName unaryOperatorClass = ClassName.get(UnaryOperator.class);
		ClassName typeNameClass = ClassName.get(TypeName.class);
		Assertions.assertIterableEquals(Collections.singleton(ParameterizedTypeName.get(unaryOperatorClass, typeNameClass)), parsed.parameters);
	}

	@Test
	public void testCheckHeadSignature() {
		// signature <E:Ljava/lang/Throwable;>(Lnet/fabricmc/mappingpoet/Signatures$HeadChecker<TE;>;)V^TE;
		String raw = "<E:Ljava/lang/Throwable;>(Lnet/fabricmc/mappingpoet/Signatures$HeadChecker<TE;>;)V^TE;";
		Signatures.MethodSignature parsed = Signatures.parseMethodSignature(raw);
		Assertions.assertIterableEquals(Collections.singleton(TypeVariableName.get("E", ClassName.get(Throwable.class))), parsed.generics);
		ClassName headCheckerClass = ClassName.get(Signatures.class).nestedClass("HeadChecker");
		Assertions.assertIterableEquals(Collections.singleton(ParameterizedTypeName.get(headCheckerClass, TypeVariableName.get("E"))), parsed.parameters);
		Assertions.assertEquals(TypeName.VOID, parsed.result);
		Assertions.assertIterableEquals(Collections.singleton(TypeVariableName.get("E")), parsed.thrown);
	}
}
