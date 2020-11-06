package codes.biscuit.skyblockaddons.asm;

import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.asm.utils.TransformerMethod;
import codes.biscuit.skyblockaddons.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Iterator;

public class GuiIngameTransformer implements ITransformer {

    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.GuiIngame.getTransformerName()};
    }

    public static void testPrint() {
        System.out.println("TEST PRINT - TRANSFORMER WORKING!");
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            if (TransformerMethod.renderScoreboard.matches(methodNode)) {

                Iterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
                methodNode.instructions.insertBefore(iterator.next(), new MethodInsnNode(Opcodes.INVOKESTATIC, "codes/biscuit/skyblockaddons/asm/GuiIngameTransformer", "testPrint", "()V", false));
                while (iterator.hasNext()) {
                    AbstractInsnNode abstractNode = iterator.next();

                    System.out.println(abstractNode.getClass().getCanonicalName());
                }
            }
        }
    }

}
