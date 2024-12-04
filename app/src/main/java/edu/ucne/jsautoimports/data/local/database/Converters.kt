package edu.ucne.jsautoimports.data.local.database

import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import edu.ucne.jsautoimports.data.local.entities.PedidosDetalleEntity
import edu.ucne.jsautoimports.data.remote.dto.CarritoDetalleDto
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }


    private val gson = Gson()

    @TypeConverter
    fun fromCarritoDetalleList(value: List<CarritoDetalleDto>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCarritoDetalleList(value: String): List<CarritoDetalleDto> {
        val listType = object : TypeToken<List<CarritoDetalleDto>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromPedidosDetalleList(value: List<PedidosDetalleEntity>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPedidosDetalleList(value: String): List<PedidosDetalleEntity> {
        val listType = object : TypeToken<List<PedidosDetalleEntity>>() {}.type
        return gson.fromJson(value, listType)
    }
}