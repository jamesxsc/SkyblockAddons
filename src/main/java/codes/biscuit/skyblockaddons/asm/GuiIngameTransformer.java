package codes.biscuit.skyblockaddons.asm;

import codes.biscuit.skyblockaddons.asm.utils.TransformerClass;
import codes.biscuit.skyblockaddons.asm.utils.TransformerMethod;
import codes.biscuit.skyblockaddons.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class GuiIngameTransformer implements ITransformer {

    @Override
    public String[] getClassName() {
        return new String[]{TransformerClass.GuiIngame.getTransformerName()};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            if (TransformerMethod.renderScoreboard.matches(methodNode)) {

                Iterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
                while (iterator.hasNext()) {
                    AbstractInsnNode abstractNode = iterator.next();

                    if ((abstractNode instanceof LineNumberNode && ((LineNumberNode) abstractNode).line == 584)) {

                        methodNode.instructions.insertBefore(abstractNode, insertSlayerPercentage());
                    }
                }
            }
        }
    }

    private InsnList insertSlayerPercentage() {
        InsnList list = new InsnList();

        list.add(new VarInsnNode(Opcodes.ALOAD, 15)); // string with playername
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "codes/biscuit/skyblockaddons/asm/hooks/GuiIngameHook", "insertSlayerPercentage",
                "(Ljava/lang/String;)Ljava/lang/String;", false));
        list.add(new VarInsnNode(Opcodes.ASTORE, 15));

        return list;
    }

}
