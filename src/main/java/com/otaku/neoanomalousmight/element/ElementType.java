package com.otaku.neoanomalousmight.element;

/**
 * 元素类型枚举类
 * 定义了所有游戏中存在的元素类型
 */
public enum ElementType {
    FIRE("Pyro", "火"),      // 火元素：高爆发与燃烧
    WATER("Hydro", "水"),      // 水元素：持续性伤害与控制
    WIND("Anemo", "风"),       // 风元素：扩散与聚怪
    THUNDER("Electro", "雷"),  // 雷元素：高频打击与感电
    GRASS("Dendro", "草"),     // 草元素：新生与绽放
    ICE("Cryo", "冰"),         // 冰元素：控制与爆发
    EARTH("Geo", "岩"),        // 岩元素：防御与护盾
    NONE("None", "无");         // 无元素

    private final String englishName;
    private final String chineseName;

    ElementType(String englishName, String chineseName) {
        this.englishName = englishName;
        this.chineseName = chineseName;
    }

    /**
     * 获取元素的英文名称
     * @return 元素英文名称
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * 获取元素的中文名称
     * @return 元素中文名称
     */
    public String getChineseName() {
        return chineseName;
    }

    @Override
    public String toString() {
        return chineseName;
    }
}