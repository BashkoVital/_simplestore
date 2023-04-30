import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable()
export class HttpOrderService {

  constructor(private http: HttpClient) {
  }

  getAllOrdersData() {
    return this.http.get<any>('http://localhost:8080/store/orders')
  }

  addOrder(data: any) {
    return this.http.post('http://localhost:8080/store/orders', data);
  }

  updateOrderById(data: any, id: number) {
    return this.http.put('http://localhost:8080/store/orders/' + id, data);
  }

  deleteOrderById(id: number) {
    return this.http.delete('http://localhost:8080/store/orders/' + id);
  }
}
