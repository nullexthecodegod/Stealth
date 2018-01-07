package nl.x.api.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.lwjgl.input.Keyboard;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
/**
 * @author NullEX
 *
 */
public @interface Info {

	String name() default "null";

	String desc() default "default";

	boolean enabled() default false;

	boolean shown() default true;

	int bind() default Keyboard.KEY_NONE;

}
