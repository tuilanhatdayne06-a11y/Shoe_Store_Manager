package dao;

import java.util.ArrayList;
import java.util.List;

import entity.NhaCungCap;

public class NhaCungCapDao {
    private final List<NhaCungCap> suppliers = new ArrayList<>();

    public List<NhaCungCap> getAll() {
        return new ArrayList<>(suppliers);
    }

    public NhaCungCap findById(String supplierId) {
        for (NhaCungCap supplier : suppliers) {
            if (supplier.getSupplierId().equalsIgnoreCase(supplierId)) {
                return supplier;
            }
        }
        return null;
    }

    public boolean insert(NhaCungCap supplier) {
        if (findById(supplier.getSupplierId()) != null) {
            return false;
        }
        suppliers.add(supplier);
        return true;
    }

    public boolean update(String oldSupplierId, NhaCungCap updatedSupplier) {
        NhaCungCap existingSupplier = findById(updatedSupplier.getSupplierId());
        if (existingSupplier != null
                && !updatedSupplier.getSupplierId().equalsIgnoreCase(oldSupplierId)) {
            return false;
        }

        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId().equalsIgnoreCase(oldSupplierId)) {
                suppliers.set(i, updatedSupplier);
                return true;
            }
        }
        return false;
    }

    public boolean delete(String supplierId) {
        NhaCungCap supplier = findById(supplierId);
        return supplier != null && suppliers.remove(supplier);
    }
}
