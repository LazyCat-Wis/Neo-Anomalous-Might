# Neo Anomalous Might - Java 文件注释汇总

本文档汇总了所有 Java 文件中的注释内容，按文件路径分层组织。

---

## Neo_Anomalous_Might.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/Neo_Anomalous_Might.java`

```java
/**
 * 模组主类
 * 负责初始化模组的所有组件和功能
 */

/**
 * 构造函数，初始化模组
 * @param context FMLJavaMod加载上下文
 */

/**
 * 模组通用初始化方法
 * @param event FML通用设置事件
 */

/**
 * 添加物品到创造模式标签
 * @param event 创造模式标签内容事件
 */

/**
 * 服务器启动事件处理
 * @param event 服务器启动事件
 */

/**
 * 客户端模组事件处理类
 */

/**
 * 客户端初始化方法
 * @param event FML客户端设置事件
 */
```

---

## ForgeBusEvents.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/core/event/ForgeBusEvents.java`

```java
/**
 * Forge事件处理类
 * 处理游戏中的各种事件，如玩家登录、攻击、伤害等
 */

/**
 * 玩家加入世界事件
 * 当玩家第一次进入世界时触发
 * @param event 实体加入世界事件
 */

/**
 * 玩家登录事件
 * 当玩家首次登录服务器时触发
 * @param event 玩家登录事件
 */

/**
 * 玩家克隆事件
 * 当玩家死亡或重生时触发
 * @param event 玩家克隆事件
 */

/**
 * 玩家攻击事件
 * 当玩家攻击实体时触发
 * @param event 实体攻击事件
 */

/**
 * 实体受到伤害事件
 * 当实体受到伤害时触发
 * @param event 实体伤害事件
 */

/**
 * 客户端事件处理类
 * 处理客户端特定的事件
 */

/**
 * 客户端玩家Tick事件
 * 每游戏刻在客户端触发一次
 * @param event 客户端Tick事件
 */

/**
 * 键盘输入事件
 * 当玩家按下键盘按键时触发
 * @param event 键盘输入事件
 */

/**
 * 服务器事件处理类
 * 处理服务器特定的事件
 */

/**
 * 服务器玩家Tick事件
 * 每游戏刻在服务器触发一次
 * @param event 服务器Tick事件
 */
```

---

## ModForgeEvents.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/core/event/ModForgeEvents.java`

```java
/**
 * Forge事件处理类
 * 处理游戏中的各种事件，如玩家登录、攻击、伤害等
 */

/**
 * 玩家加入世界事件
 * 当玩家第一次进入世界时触发
 * @param event 实体加入世界事件
 */

/**
 * 玩家登录事件
 * 当玩家首次登录服务器时触发
 * @param event 玩家登录事件
 */

/**
 * 玩家克隆事件
 * 当玩家死亡或重生时触发
 * @param event 玩家克隆事件
 */

/**
 * 玩家攻击事件
 * 当玩家攻击实体时触发
 * @param event 实体攻击事件
 */

/**
 * 实体受到伤害事件
 * 当实体受到伤害时触发
 * @param event 实体伤害事件
 */

/**
 * 客户端事件处理类
 * 处理客户端特定的事件
 */

/**
 * 客户端玩家Tick事件
 * 每游戏刻在客户端触发一次
 * @param event 客户端Tick事件
 */

/**
 * 键盘输入事件
 * 当玩家按下键盘按键时触发
 * @param event 键盘输入事件
 */

/**
 * 服务器事件处理类
 * 处理服务器特定的事件
 */

/**
 * 服务器玩家Tick事件
 * 每游戏刻在服务器触发一次
 * @param event 服务器Tick事件
 */
```

---

## ModRegistration.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/init/ModRegistration.java`

```java
/**
 * 统一注册管理器
 * 使用正确的Forge API创建自定义注册表
 */

/**
 * 初始化注册系统
 * @param modEventBus 模组事件总线
 */

/**
 * 处理自定义注册表创建事件
 */

/**
 * 获取元素类型注册表实例
 */

/**
 * 获取角色注册表实例
 */

/**
 * 处理实体属性创建事件
 */
```

---

## NetworkHandler.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/core/network/NetworkHandler.java`

```java
/**
 * 网络通信处理类
 * 管理客户端和服务器之间的通信通道和数据包
 */

/**
 * 注册所有网络数据包
 */
```

---

## C2SChooseRolePacket.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/core/network/C2SChooseRolePacket.java`

```java
/**
 * 客户端到服务器的角色选择数据包
 * 用于玩家选择角色并通知服务器
 */

/**
 * 构造函数
 * @param role 选择的角色
 */

/**
 * 构造函数
 * @param roleId 角色的资源位置
 */

/**
 * 编码数据包
 * @param message 数据包对象
 * @param buffer 字节缓冲区
 */

/**
 * 解码数据包
 * @param buffer 字节缓冲区
 * @return 解码后的数据包对象
 */

/**
 * 处理数据包
 * @param message 数据包对象
 * @param context 网络事件上下文
 */
```

---

## S2CSyncRolePacket.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/core/network/S2CSyncRolePacket.java`

```java
/**
 * 服务器到客户端的角色数据同步数据包
 * 用于将玩家的角色数据从服务器同步到客户端
 */

/**
 * 构造函数
 * @param roleCapability 玩家角色能力
 */

/**
 * 构造函数
 * @param playerRoleData 玩家角色数据NBT
 */

/**
 * 编码数据包
 * @param message 数据包对象
 * @param buffer 字节缓冲区
 */

/**
 * 解码数据包
 * @param buffer 字节缓冲区
 * @return 解码后的数据包对象
 */

/**
 * 处理数据包
 * @param message 数据包对象
 * @param context 网络事件上下文
 */
```

---

## Elements.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/element/Elements.java`

```java
/**
 * 元素系统核心管理类
 * 负责注册和管理所有元素相关的内容
 */

/**
 * 注册元素类型
 * @param elementType 元素类型
 * @return 注册对象
 */

/**
 * 获取元素类型的资源位置
 * @param elementType 元素类型
 * @return 资源位置
 */

/**
 * 获取元素类型的显示名称
 * @param elementType 元素类型
 * @return 显示名称
 */

/**
 * 获取元素类型的英文名称
 * @param elementType 元素类型
 * @return 英文名称
 */
```

---

## ElementType.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/element/ElementType.java`

```java
/**
 * 元素类型枚举类
 * 定义了所有游戏中存在的元素类型
 */

/**
 * 获取元素的英文名称
 * @return 元素英文名称
 */

/**
 * 获取元素的中文名称
 * @return 元素中文名称
 */
```

---

## ElementalAttributes.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/element/ElementalAttributes.java`

```java
/**
 * 元素属性类
 * 管理元素之间的相克关系和元素对角色属性的影响
 */

/**
 * 初始化元素相克关系
 */

/**
 * 初始化元素对角色基础属性的影响
 */

/**
 * 设置元素关系
 * @param attacker 攻击者元素类型
 * @param defender 被攻击者元素类型
 * @param damageMultiplier 伤害倍率
 * @param defenseChange 防御变化百分比
 * @param evasionChange 闪避变化百分比
 */

/**
 * 获取元素伤害倍率
 * @param attacker 攻击者元素类型
 * @param defender 被攻击者元素类型
 * @return 伤害倍率
 */

/**
 * 获取元素防御变化百分比
 * @param attacker 攻击者元素类型
 * @param defender 被攻击者元素类型
 * @return 防御变化百分比
 */

/**
 * 获取元素闪避变化百分比
 * @param attacker 攻击者元素类型
 * @param defender 被攻击者元素类型
 * @return 闪避变化百分比
 */

/**
 * 获取元素对基础属性的影响
 * @param element 元素类型
 * @param attribute 属性名称
 * @return 属性影响值
 */
```

---

## ElementalInteraction.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/element/ElementalInteraction.java`

```java
/**
 * 元素交互类
 * 处理元素之间的相互作用和效果
 */

/**
 * 应用元素效果到目标实体
 * @param attacker 攻击者
 * @param target 目标实体
 * @param attackerElement 攻击者元素类型
 * @param targetElement 目标元素类型
 */

/**
 * 应用火元素效果
 * @param target 目标实体
 * @param targetElement 目标元素类型
 */

/**
 * 应用水元素效果
 * @param target 目标实体
 * @param targetElement 目标元素类型
 */

/**
 * 应用风元素效果
 * @param target 目标实体
 * @param targetElement 目标元素类型
 */

/**
 * 应用雷元素效果
 * @param target 目标实体
 * @param targetElement 目标元素类型
 */

/**
 * 应用草元素效果
 * @param target 目标实体
 * @param targetElement 目标元素类型
 */

/**
 * 应用冰元素效果
 * @param target 目标实体
 * @param targetElement 目标元素类型
 */

/**
 * 应用岩元素效果
 * @param target 目标实体
 * @param targetElement 目标元素类型
 */

/**
 * 计算元素反应伤害
 * @param baseDamage 基础伤害
 * @param attackerElement 攻击者元素类型
 * @param targetElement 目标元素类型
 * @return 计算后的伤害
 */
```

---

## DebugRoleCommand.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/client/commands/DebugRoleCommand.java`

```java
/**
 * 调试命令类，用于测试角色选择界面
 */

/**
 * 注册客户端命令
 * @param event 命令注册事件
 */

---

## ModRoles.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/role/ModRoles.java`

```java
/**
 * 角色注册管理类
 * 负责注册和管理所有游戏角色
 */

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

/**
 * 获取所有已注册的角色
 * @return 所有角色的列表
 */

/**
 * 根据资源位置获取角色
 * @param resourceLocation 角色的资源位置
 * @return 角色对象，如果不存在则返回null
 */

/**
 * 获取角色的资源位置
 * @param role 角色
 * @return 资源位置
 */
```

---

## RoleManager.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/role/RoleManager.java`

```java
/**
 * 角色管理器接口
 * 提供统一的角色系统访问入口，连接到新的capability系统
 * 所有角色相关的方法都应该通过此接口进行访问
 */

/**
 * 获取玩家的角色能力
 * @param player 玩家对象
 * @return 角色能力的LazyOptional
 */

/**
 * 安全地获取玩家的角色能力
 * 如果玩家没有角色能力，则返回一个默认实现
 * @param player 玩家对象
 * @return 角色能力实例
 */

/**
 * 检查玩家是否已经选择了角色
 * @param player 玩家对象
 * @return 如果玩家已经选择角色则返回true，否则返回false
 */

/**
 * 获取玩家选择的角色
 * @param player 玩家对象
 * @return 玩家选择的角色，如果没有选择则返回null
 */

/**
 * 设置玩家选择的角色
 * @param player 玩家对象
 * @param role 要设置的角色
 */
```

---

## PlayerRoleProvider.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/capability/player/PlayerRoleProvider.java`

```java
/**
 * 玩家角色能力提供者
 * 为玩家实体附加角色能力系统
 */

/**
 * 获取能力
 * @param cap 能力类型
 * @param side 方向
 * @return 能力的LazyOptional
 */

/**
 * 序列化能力数据为NBT
 * @return 包含能力数据的CompoundTag
 */

/**
 * 从NBT反序列化能力数据
 * @param nbt 包含能力数据的CompoundTag
 */

/**
 * 附加能力到玩家实体
 * @param event 附加能力事件
 */
```

---

## Role.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/role/Role.java`

```java
/**
 * 角色类
 * 定义了角色的基本属性和信息
 */

/**
 * 构造函数
 * @param registryName 注册名称
 * @param name 角色名称
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
 */

/**
 * 获取角色名称
 * @return 角色名称
 */

/**
 * 获取角色描述
 * @return 角色描述
 */

/**
 * 获取元素类型
 * @return 元素类型
 */

/**
 * 获取基础生命值
 * @return 基础生命值
 */

/**
 * 获取基础攻击力
 * @return 基础攻击力
 */

/**
 * 获取注册名称
 * @return 注册名称
 */

/**
 * 获取基础防御力
 * @return 基础防御力
 */

/**
 * 获取基础法力值
 * @return 基础法力值
 */

/**
 * 获取基础暴击几率
 * @return 基础暴击几率
 */

/**
 * 获取基础暴击伤害
 * @return 基础暴击伤害
 */

/**
 * 获取基础闪避几率
 * @return 基础闪避几率
 */

/**
 * 获取基础移动速度
 * @return 基础移动速度
 */

/**
 * 获取角色的翻译键
 * @return 翻译键
 */
```

---

## Config.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/common/Config.java`

```java
/**
 * An example config class. This is not required, but it's a good idea to have one to keep your config organized.
 * Demonstrates how to use Forge's config APIs
 */

/**
 * Whether to log the dirt block on common setup
 */

/**
 * A magic number
 */

/**
 * What you want the introduction message to be for the magic number
 */

/**
 * A list of items to log on common setup.
 */
```

---

## PlayerAttributeEventHandler.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/core/event/PlayerAttributeEventHandler.java`

```java
/**
 * 玩家属性事件处理器
 * 将角色属性直接附加到玩家实体上
 */

/**
 * 当玩家加入世界时应用属性
 */

/**
 * 当玩家重新生成时应用属性
 */

/**
 * 当玩家数据加载时应用属性
 */

/**
 * 将角色属性应用到玩家实体
 */

/**
 * 应用角色特定的属性
 */

/**
 * 应用属性修饰符
 */

/**
 * 移除所有角色相关的属性修饰符
 */

/**
 * 移除属性修饰符
 */
```

---

## HealthAttributeModifier.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/core/util/HealthAttributeModifier.java`

```java
/**
 * Utility class for health attribute modifiers
 */

/**
 * Unique ID for the health modifier used by player roles
 */
```

---

## ModItem.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/common/ModItem.java`

```java
/**
 * 模组物品注册管理类
 * 负责注册所有游戏中的自定义物品
 */

/**
 * 注册物品到事件总线
 * @param eventBus 模组事件总线
 */
```

---

## InventoryOverlayEventHandler.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/client/event/InventoryOverlayEventHandler.java`

```java
/**
 * 玩家物品栏属性显示覆盖层
 * 在生存物品栏界面显示玩家角色属性
 */

/**
 * 当屏幕渲染时调用的事件处理方法
 * @param event 屏幕渲染事件
 */

/**
 * 绘制背景
 */

/**
 * 绘制角色名称
 */

/**
 * 绘制属性信息
 */
```

---

## InventoryTabEventHandler.java

**文件路径**: `src/main/java/com/otaku/neoanomalousmight/client/event/InventoryTabEventHandler.java`

```java
/**
 * 物品栏标签事件处理器
 * 为生存物品栏界面添加标签页功能
 */

/**
 * 当屏幕初始化时调用的事件处理方法
 * @param event 屏幕初始化事件
 */

/**
 * 当屏幕渲染时调用的事件处理方法
 * @param event 屏幕渲染事件
 */

/**
 * 绘制标签内容
 */

/**
 * 绘制角色状态内容
 */

/**
 * 绘制技能内容
 */

/**
 * 当屏幕关闭时调用的事件处理方法
 * @param event 屏幕关闭事件
 */

/**
 * 获取当前选中的标签
 * @param screen 屏幕对象
 * @return 选中标签的索引
 */
```

---