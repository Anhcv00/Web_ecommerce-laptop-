package com.shopecommerce.services;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shopecommerce.GeneratePassword;
import com.shopecommerce.entities.Role;
import com.shopecommerce.entities.User;
import com.shopecommerce.repositories.RoleRepo;
import com.shopecommerce.repositories.UserRepo;

@Service
public class UserService {
	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	public UserRepo userRepo;

	@Autowired
	public RoleRepo roleRepo;

	public User findUserById(final int id) {
		String sql = "SELECT * FROM tbl_users WHERE id = :id";
		Query query = entityManager.createNativeQuery(sql, User.class);
		query.setParameter("id", id);

		List<User> results = query.getResultList();
		if (results.isEmpty()) {
			return null; // Không tìm thấy người dùng
		}
		return results.get(0);
	}

	public Role findRoleById(final int id) {
		String sql = "SELECT * FROM tbl_roles WHERE id = :id";
		Query query = entityManager.createNativeQuery(sql, Role.class);
		query.setParameter("id", id);

		List<Role> results = query.getResultList();
		if (results.isEmpty()) {
			return null; // Không tìm thấy vai trò
		}
		return results.get(0);
	}

	private boolean isEmptyUploadFile(MultipartFile[] images) {
		if (images == null || images.length == 0) {
			return true;
		}
		for (MultipartFile image : images) {
			if (image.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public User loadUserByUsername(String userName) {
		try {
			String jpql = "FROM User u WHERE u.username = :username";
			Query query = entityManager.createQuery(jpql, User.class);
			query.setParameter("username", userName);

			List<User> results = query.getResultList();
			if (results.isEmpty()) {
				return null; // Không tìm thấy người dùng
			}
			return results.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(rollbackOn = Exception.class)
	public void saveUser(MultipartFile[] images, User user) throws Exception {
		try {
			if (user.getId() != null) { // chỉnh sửa
				User userInDb = userRepo.findById(user.getId())
						.orElseThrow(() -> new Exception("User not found"));

				// Lấy avatar cũ
				String oldAvatar = userInDb.getAvatar();

				if (!isEmptyUploadFile(images)) { // Nếu có ảnh mới
					// Xóa ảnh cũ
					if (oldAvatar != null && !oldAvatar.isEmpty()) {
						File oldFile = new File("F:\\Project\\Web_ecommerce-laptop-\\upload_avt" + oldAvatar);
						if (oldFile.exists()) {
							oldFile.delete();
						}
					}

					// Lưu ảnh mới và cập nhật avatar
					for (MultipartFile image : images) {
						File directory = new File("F:\\Project\\Web_ecommerce-laptop-\\upload_avt");
						if (!directory.exists()) {
							directory.mkdirs();
						}

						File newFile = new File(directory, image.getOriginalFilename());
						image.transferTo(newFile);
						user.setAvatar(image.getOriginalFilename());
					}
				} else {
					// Giữ nguyên avatar cũ nếu không có ảnh mới
					user.setAvatar(oldAvatar);
				}
			} else if (!isEmptyUploadFile(images)) { // Nếu là người dùng mới và có ảnh
				for (MultipartFile image : images) {
					File directory = new File("D:/Shop/upload_avt/");
					if (!directory.exists()) {
						directory.mkdirs();
					}

					File newFile = new File(directory, image.getOriginalFilename());
					image.transferTo(newFile);
					user.setAvatar(image.getOriginalFilename());
				}
			}

			// Cập nhật thời gian
			LocalDateTime now = LocalDateTime.now();
			if (user.getCreatedDate() != null) {
				user.setUpdatedDate(now);
			} else {
				user.setCreatedDate(now);
				user.setUpdatedDate(now);
			}

			userRepo.save(user);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Error while saving user", e);
		}
	}

	@Transactional(rollbackOn = Exception.class)
	public void saveGuestUser(User user) throws Exception {
		try {
			user.setPassword(GeneratePassword.GenerPass(user.getPassword()));
			user.getRoles().add(findRoleById(2)); // Thêm vai trò khách
			LocalDateTime now = LocalDateTime.now();
			user.setUpdatedDate(now);
			user.setCreatedDate(now);
			user.setStatus(true);
			user.setAvatar(""); // Không có ảnh đại diện
			userRepo.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while saving guest user", e);
		}
	}
}
