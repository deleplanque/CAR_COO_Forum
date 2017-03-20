package forum.utils;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * MyFactory - Fabrique g�n�rique qui va renvoyer une instance de
 * l'impl�mentation associ�e � une interface. L'interface doit-�tre nomm�e
 * IXxxxx et son impl�mentation Xxxxx.
 * 
 * @version 1.0
 * 
 */
public class MyFactory {
	/**
	 * Renvoie une instance d'impl�mentation li�e � une interface nomm�e IXxxx
	 * 
	 * @param clazzInterface
	 *            Nom de l'interface pour laquelle on d�sire r�cup�rer une
	 *            instance.
	 * @return une instance qui impl�mente classInterface
	 */
	public static Object getInstance(final Class<?> clazzInterface) {
		Object result = null;

		if (!clazzInterface.isInterface()) {
			throw new IllegalArgumentException("Ceci n'est pas une interface!");
		}
		final String interfaceName = clazzInterface.getSimpleName();
		final String className = clazzInterface.getPackage().getName() + "." + interfaceName.substring(1);

		Class<?> clazzToInstance = null;
		try {
			clazzToInstance = Class.forName(className);
		} catch (final ClassNotFoundException e1) {
			throw new IllegalArgumentException("Aucune impl�mentation trouv�e pour cette interface!");
		}

		try {
			final Method methodToCall = clazzToInstance.getMethod("getInstance", new Class[0]);
			result = methodToCall.invoke(null, new Object[0]);
		} catch (final SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException("Probl�me de s�curit�");
		} catch (final NoSuchMethodException e) {
			throw new IllegalArgumentException("La classe d'impl�mentation ne respecte pas le pattern singleton (getInstance)!");
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("Acc�s aux invocation interdit!");
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException("Probl�me d'invocation!");
		}
		return result;
	}
}
