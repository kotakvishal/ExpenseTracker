package ktk.wishdroid.expensetracker.data.local.converters

import androidx.room.TypeConverter
import ktk.wishdroid.expensetracker.data.local.entities.CategoryEntity

class CategoryConverter {

    @TypeConverter
    fun fromCategory(category: CategoryEntity): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(value: String): CategoryEntity {
        return CategoryEntity.valueOf(value)
    }
}
