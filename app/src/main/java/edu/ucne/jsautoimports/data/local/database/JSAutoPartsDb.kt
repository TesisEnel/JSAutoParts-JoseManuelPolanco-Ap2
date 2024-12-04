package edu.ucne.jsautoimports.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.jsautoimports.data.local.dao.CarritoDao
import edu.ucne.jsautoimports.data.local.dao.CarritoDetalleDao
import edu.ucne.jsautoimports.data.local.dao.CategoriaDao
import edu.ucne.jsautoimports.data.local.dao.PedidoDao
import edu.ucne.jsautoimports.data.local.dao.PedidosDetalleDao
import edu.ucne.jsautoimports.data.local.dao.PiezaDao
import edu.ucne.jsautoimports.data.local.dao.UsuarioDao
import edu.ucne.jsautoimports.data.local.entities.CarritoDetalleEntity
import edu.ucne.jsautoimports.data.local.entities.CarritoEntity
import edu.ucne.jsautoimports.data.local.entities.CategoriaEntity
import edu.ucne.jsautoimports.data.local.entities.PedidoEntity
import edu.ucne.jsautoimports.data.local.entities.PedidosDetalleEntity
import edu.ucne.jsautoimports.data.local.entities.PiezaEntity
import edu.ucne.jsautoimports.data.local.entities.UsuarioEntity

@Database(
    entities = [
        CategoriaEntity::class,
        PiezaEntity::class,
        UsuarioEntity::class,
        CarritoEntity::class,
        CarritoDetalleEntity::class,
        PedidoEntity::class,
        PedidosDetalleEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class JSAutoPartsDb: RoomDatabase() {
    abstract val categoriaDao: CategoriaDao
    abstract val piezaDao: PiezaDao
    abstract val usuarioDao: UsuarioDao
    abstract val carritoDao: CarritoDao
    abstract val carritoDetalleDao: CarritoDetalleDao
    abstract val pedidoDao: PedidoDao
    abstract val pedidoDetalleDao: PedidosDetalleDao



}