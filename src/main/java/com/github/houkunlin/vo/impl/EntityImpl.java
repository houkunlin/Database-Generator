package com.github.houkunlin.vo.impl;

import com.github.houkunlin.vo.IEntity;
import com.github.houkunlin.vo.IEntityField;
import com.google.common.base.CaseFormat;
import com.intellij.database.psi.DbTable;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 实体类信息
 *
 * @author HouKunLin
 * @date 2020/5/28 0028 0:59
 */
@Data
public class EntityImpl implements IEntity {
    private String name;
    private String comment;
    private Set<String> packages = new HashSet<>();
    private String packageString = "";

    public EntityImpl(DbTable dbTable) {
        this.name = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dbTable.getName());
        this.comment = StringUtils.defaultString(dbTable.getComment(), "");
    }

    @Override
    public String getNameFirstLower() {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, name);
    }

    @Override
    public String getNameFirstUpper() {
        return name;
    }

    public void setPackages(List<? extends IEntityField> fields) {
        packages.clear();
        for (IEntityField field : fields) {
            String fullTypeName = field.getFullTypeName();
            if (!fullTypeName.startsWith("java.lang.")) {
                packages.add(fullTypeName);
            }
        }
        this.packageString = packages.stream().map(item -> String.format("import %s;\n", item)).collect(Collectors.joining());
    }
}
