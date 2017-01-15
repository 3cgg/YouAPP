package j.jave.kernal.streaming.netty.controller;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;

public class ParameterNameGet {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(ParameterNameGet.class);
	
	
	public static String[] getMethodParameterNamesByAsm4(Class<?> clazz, final Method method,ClassLoader classLoader) {  
        final Class<?>[] parameterTypes = method.getParameterTypes();  
        if (parameterTypes == null || parameterTypes.length == 0) {  
            return null;  
        }  
        final Type[] types = new Type[parameterTypes.length];  
        for (int i = 0; i < parameterTypes.length; i++) {  
            types[i] = Type.getType(parameterTypes[i]);  
        }  
        final String[] parameterNames = new String[parameterTypes.length];  
        try {
        	if(classLoader==null){
        		classLoader=clazz.getClassLoader();
        	}
        	InputStream is=classLoader.getResourceAsStream(clazz.getName().replace('.', '/')
                        + ".class");
            ClassReader classReader = new ClassReader(is);  
            classReader.accept(new ClassVisitor(Opcodes.ASM5) {  
                @Override  
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {  
                    if(LOGGER.isDebugEnabled()){
	                	LOGGER.debug("method name : "+name+" ; access : "+access 
	                				+" ; desc : "+desc +"; signature : "+signature+JJSON.get().formatObject(exceptions));
                    }
                	Type[] argumentTypes = Type.getArgumentTypes(desc);  
                    if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {  
                        return null;  
                    }  
                    return new MethodVisitor(Opcodes.ASM5) {  
                        @Override  
                        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {  
                            if (Modifier.isStatic(method.getModifiers())) {  
                                parameterNames[index] = name;  
                            }  
                            else if (index > 0&&index<=parameterNames.length) {  
                                parameterNames[index - 1] = name;  
                            }  
                        }  
                    };  
  
                }  
            }, 0);  
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return parameterNames;  
    }  
}
