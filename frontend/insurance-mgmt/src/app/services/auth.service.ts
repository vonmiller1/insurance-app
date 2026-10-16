import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly apiUrl = 'http://localhost:8080/app';

  constructor(private http: HttpClient, private router: Router) {}

  register(data: any): Observable<any> {
    const payload: any = {
      username: data.username,
      firstName: data.firstName,
      lastName: data.lastName,
      email: data.email,
      password: data.password,
      role: data.role,
      address: data.address,
      phoneNumber: data.phoneNumber,
      department: data.department,
      designation: data.designation,
      licenseNumber: data.licenseNumber
    };

    let endpoint = '';
    switch (data.role) {
      case 'CUSTOMER':
        endpoint = `${this.apiUrl}/register/customer`;
        break;
      case 'AGENT':
        endpoint = `${this.apiUrl}/register/agent`;
        break;
      case 'EMPLOYEE':
        endpoint = `${this.apiUrl}/register/employee`;
        break;
      default:
        throw new Error('Invalid role selected');
    }

    return this.http.post(endpoint, payload);
  }

  getRoleName(): string | null {
    const token = localStorage.getItem('accessToken');
    if (!token) {
      return null;
    }

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.role || null;
    } catch {
      return null;
    }
  }

  getUserId(): number {
    const userId = localStorage.getItem('userId');
    return userId ? Number(userId) : 0;
  }

  loginAndRedirect(data: any, captchaResolved: boolean): Observable<any> {
    if (!data.username || !data.password || !captchaResolved) {
      return throwError(() => new Error('Invalid login data or CAPTCHA not resolved'));
    }

    return this.http.post(`${this.apiUrl}/login`, data).pipe(
      tap((response: any) => {
        localStorage.setItem('userId', String(response.userId));
        localStorage.setItem('accessToken', response.accessToken);
        localStorage.setItem('userRole', response.role);
        if (response.licenseNumber) {
          localStorage.setItem('licenseNumber', response.licenseNumber);
        }
        if (response.name) {
          localStorage.setItem('name', response.name);
        }

        const role = response.role || this.getRoleName();
        this.redirectBasedOnRole(role);
      })
    );
  }

  private redirectBasedOnRole(roleName: string | null) {
    switch (roleName?.toUpperCase()) {
      case 'ADMIN':
        this.router.navigate(['/admin/dashboard']);
        break;
      case 'CUSTOMER':
        this.router.navigate(['/customer/dashboard']);
        break;
      case 'AGENT':
        this.router.navigate(['/agent/dashboard']);
        break;
      case 'EMPLOYEE':
        this.router.navigate(['/employee/dashboard']);
        break;
      default:
        this.router.navigate(['/login']);
    }
  }

  getToken(): string {
    return localStorage.getItem('accessToken') || sessionStorage.getItem('accessToken') || '';
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  hasRole(role: string): boolean {
    return this.getRoleName() === role;
  }
}