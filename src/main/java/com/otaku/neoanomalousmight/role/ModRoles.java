package com.otaku.neoanomalousmight.role;

import com.otaku.neoanomalousmight.Neo_Anomalous_Might;
import com.otaku.neoanomalousmight.element.ElementType;
import com.otaku.neoanomalousmight.init.ModRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
// import net.minecraftforge.registries.ForgeRegistries; // 未使用的导入

import javax.annotation.Nullable;

/**
 * 角色注册管理类
 * 负责注册和管理所有游戏角色
 */
public class ModRoles {
    public static final DeferredRegister<Role> ROLES = DeferredRegister.create(ModRegistration.ROLE_REGISTRY_KEY, Neo_Anomalous_Might.MOD_ID);

    public static void registerRoles() {
        // 角色会在游戏启动时自动注册
    }

    // 注册角色：苏林（Su Lin）
    // 擅长物理攻击和法术，元素属性暂时未确定，这里使用风元素作为示例
    public static final RegistryObject<Role> SU_LIN = registerRole(
            "su_lin",
            "苏林",
            "擅长物理攻击和法术，可根据装备选择不同的培养方向",
            ElementType.WIND,
            100.0f,   // 基础生命值
            20.0f,    // 基础攻击力
            15.0f,    // 基础防御力
            80.0f,    // 基础法力值
            0.05f,    // 基础暴击几率 (5%)
            1.5f,     // 基础暴击伤害 (150%)
            0.08f,    // 基础闪避几率 (8%)
            1.0f      // 基础移动速度
    );

    // 注册角色：苏皖（Su Wan）
    // 擅长法术攻击，元素属性暂时未确定，这里使用雷元素作为示例
    public static final RegistryObject<Role> SU_WAN = registerRole(
            "su_wan",
            "苏皖",
            "擅长法术攻击，拥有强大的元素控制能力",
            ElementType.THUNDER,
            80.0f,    // 基础生命值
            15.0f,    // 基础攻击力
            10.0f,    // 基础防御力
            120.0f,   // 基础法力值
            0.10f,    // 基础暴击几率 (10%)
            1.8f,     // 基础暴击伤害 (180%)
            0.12f,    // 基础闪避几率 (12%)
            1.1f      // 基础移动速度
    );

    /**
     * 注册角色
     * @param name 注册名称
     * @param displayName 显示名称
     * @param description 角色描述
     * @param elementType 元素类型
     * @param baseHealth 基础生命值
     * @param baseAttack 基础攻击力
     * @param baseDefense 基础防御力
     * @param baseMana 基础法力值
     * @param baseCritChance 基础暴击几率
     * @param baseCritDamage 基础暴击伤害
     * @param baseEvasion 基础闪避几率
     * @param baseMovementSpeed 基础移动速度
     * @return 注册对象
     */
    private static RegistryObject<Role> registerRole(String name, String displayName, String description, ElementType elementType,
                                                     float baseHealth, float baseAttack, float baseDefense, float baseMana,
                                                     float baseCritChance, float baseCritDamage, float baseEvasion, float baseMovementSpeed) {
        return ROLES.register(name, () -> new Role(
                ResourceLocation.fromNamespaceAndPath(Neo_Anomalous_Might.MOD_ID, name),
                displayName,
                description,
                elementType,
                baseHealth,
                baseAttack,
                baseDefense,
                baseMana,
                baseCritChance,
                baseCritDamage,
                baseEvasion,
                baseMovementSpeed
        ));
    }

    /**
     * 获取所有已注册的角色
     * @return 所有角色的列表
     */
    public static java.util.List<Role> getAllRoles() {
        return ROLES.getEntries().stream()
                .map(RegistryObject::get)
                .toList();
    }

    /**
     * 根据资源位置获取角色
     * @param resourceLocation 角色的资源位置
     * @return 角色对象，如果不存在则返回null
     */
    @Nullable
    public static Role getRole(ResourceLocation resourceLocation) {
        return ROLES.getEntries().stream()
                .map(RegistryObject::get)
                .filter(role -> role.getRegistryName().equals(resourceLocation))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取角色的资源位置
     * @param role 角色
     * @return 资源位置
     */
    public static ResourceLocation getRoleResourceLocation(Role role) {
        return role.getRegistryName();
    }
}
