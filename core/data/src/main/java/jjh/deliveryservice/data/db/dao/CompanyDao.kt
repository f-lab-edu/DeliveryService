package jjh.deliveryservice.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jjh.deliveryservice.data.db.entity.CompanyEntity

@Dao
interface CompanyDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCompanyInfo(companyList: List<CompanyEntity>)

  @Query("SELECT * FROM CompanyEntity")
  suspend fun getAll(): List<CompanyEntity>

  @Query("DELETE FROM CompanyEntity")
  suspend fun deleteAll()
}