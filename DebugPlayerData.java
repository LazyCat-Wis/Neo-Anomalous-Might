import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import java.io.File;
import java.io.IOException;

public class DebugPlayerData {
    public static void main(String[] args) {
        try {
            // 读取玩家数据文件
            File playerDataFile = new File("run/saves/新的世界/playerdata/380df991-f603-344c-a090-369bad2a924a.dat");
            CompoundTag playerNbt = NbtIo.read(playerDataFile);
            
            if (playerNbt == null) {
                System.out.println("无法读取玩家数据文件");
                return;
            }
            
            System.out.println("玩家数据文件内容:");
            printNbt(playerNbt, 0);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void printNbt(CompoundTag nbt, int indent) {
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            prefix.append("  ");
        }
        
        for (String key : nbt.getAllKeys()) {
            System.out.println(prefix + key + ": " + nbt.get(key).getAsString());
        }
    }
}