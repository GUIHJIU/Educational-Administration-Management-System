package com.example.studentmanagementsystemtest.controller;

import com.example.studentmanagementsystemtest.entity.Admin;
import com.example.studentmanagementsystemtest.service.Adminservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AdminController 单元测试类
 */
public class AdminControllerTest {

    @Mock
    private Adminservice adminservice;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 测试用例1：添加管理员成功
     */
    @Test
    public void testAddAdmin_Success_Returns200() {
        Admin admin = new Admin();
        when(adminservice.save(admin)).thenReturn(true);

        ResponseEntity< Integer > response = adminController.add(admin);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(200, response.getBody());

        verify(adminservice, times(1)).save(admin);
    }

    /**
     * 测试用例2：添加管理员失败
     */
    @Test
    public void testAddAdmin_Failure_Returns400() {
        Admin admin = new Admin();
        when(adminservice.save(admin)).thenReturn(false);

        ResponseEntity< Integer > response = adminController.add(admin);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(400, response.getBody());

        verify(adminservice, times(1)).save(admin);
    }

    /**
     * 测试用例3：添加管理员时发生异常
     */
    @Test
    public void testAddAdmin_Exception_Returns500() {
        Admin admin = new Admin();
        when(adminservice.save(admin)).thenThrow(new RuntimeException("数据库异常"));

        ResponseEntity< Integer > response = adminController.add(admin);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals(5000, response.getBody());

        verify(adminservice, times(1)).save(admin);
    }

    /**
     * 测试用例4：获取管理员列表成功
     */
    @Test
    public void testGetAdminList_Success_ReturnsAdminList() {
        Admin admin1 = new Admin();
        admin1.setId(1);
        admin1.setAdminNum("A001");
        admin1.setAdminName("管理员1");

        Admin admin2 = new Admin();
        admin2.setId(2);
        admin2.setAdminNum("A002");
        admin2.setAdminName("管理员2");

        List< Admin > adminList = Arrays.asList(admin1, admin2);
        when(adminservice.list()).thenReturn(adminList);

        ResponseEntity< List< Admin > > response = adminController.getAdminList();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("管理员1", response.getBody().get(0).getAdminName());
        assertEquals("管理员2", response.getBody().get(1).getAdminName());

        verify(adminservice, times(1)).list();
    }

    /**
     * 测试用例5：获取管理员列表时发生异常
     */
    @Test
    public void testGetAdminList_Exception_Returns500() {
        when(adminservice.list()).thenThrow(new RuntimeException("数据库异常"));

        ResponseEntity< List< Admin > > response = adminController.getAdminList();

        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());

        verify(adminservice, times(1)).list();
    }

    /**
     * 测试用例6：更新管理员成功
     */
    @Test
    public void testUpdateAdmin_Success_Returns200() {
        Admin admin = new Admin();
        when(adminservice.updateById(admin)).thenReturn(true);

        ResponseEntity< Integer > response = adminController.updateAdmin(admin);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(200, response.getBody());

        verify(adminservice, times(1)).updateById(admin);
    }

    /**
     * 测试用例7：更新管理员失败
     */
    @Test
    public void testUpdateAdmin_Failure_Returns400() {
        Admin admin = new Admin();
        when(adminservice.updateById(admin)).thenReturn(false);

        ResponseEntity< Integer > response = adminController.updateAdmin(admin);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(400, response.getBody());

        verify(adminservice, times(1)).updateById(admin);
    }

    /**
     * 测试用例8：更新管理员时发生异常
     */
    @Test
    public void testUpdateAdmin_Exception_Returns500() {
        Admin admin = new Admin();
        when(adminservice.updateById(admin)).thenThrow(new RuntimeException("数据库异常"));

        ResponseEntity< Integer > response = adminController.updateAdmin(admin);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals(5000, response.getBody());

        verify(adminservice, times(1)).updateById(admin);
    }

    /**
     * 测试用例9：根据ID获取管理员成功
     */
    @Test
    public void testGetAdminById_Success_ReturnsAdmin() {
        Admin admin = new Admin();
        admin.setId(1);
        admin.setAdminName("管理员1");
        when(adminservice.getById(1)).thenReturn(admin);

        ResponseEntity< Admin > response = adminController.getAdminById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("管理员1", response.getBody().getAdminName());

        verify(adminservice, times(1)).getById(1);
    }

    /**
     * 测试用例10：根据ID获取管理员但未找到
     */
    @Test
    public void testGetAdminById_NotFound_Returns404() {
        when(adminservice.getById(1)).thenReturn(null);

        ResponseEntity< Admin > response = adminController.getAdminById(1);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());

        verify(adminservice, times(1)).getById(1);
    }

    /**
     * 测试用例11：根据ID获取管理员时发生异常
     */
    @Test
    public void testGetAdminById_Exception_Returns500() {
        when(adminservice.getById(1)).thenThrow(new RuntimeException("数据库异常"));

        ResponseEntity< Admin > response = adminController.getAdminById(1);

        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());

        verify(adminservice, times(1)).getById(1);
    }

    /**
     * 测试用例12：根据ID删除管理员成功
     */
    @Test
    public void testDeleteAdminById_Success_Returns200() {
        when(adminservice.removeById(1)).thenReturn(true);

        ResponseEntity< Integer > response = adminController.deleteAdminById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(200, response.getBody());

        verify(adminservice, times(1)).removeById(1);
    }

    /**
     * 测试用例13：根据ID删除管理员失败
     */
    @Test
    public void testDeleteAdminById_Failure_Returns400() {
        when(adminservice.removeById(1)).thenReturn(false);

        ResponseEntity< Integer > response = adminController.deleteAdminById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(400, response.getBody());

        verify(adminservice, times(1)).removeById(1);
    }

    /**
     * 测试用例14：根据ID删除管理员时发生异常
     */
    @Test
    public void testDeleteAdminById_Exception_Returns500() {
        when(adminservice.removeById(1)).thenThrow(new RuntimeException("数据库异常"));

        ResponseEntity< Integer > response = adminController.deleteAdminById(1);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals(5000, response.getBody());

        verify(adminservice, times(1)).removeById(1);
    }
}
