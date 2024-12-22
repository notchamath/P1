import { UserInterface } from './UserInterface';

export interface ReimbursementInterface {
    reimbId?: number;
    description: string;
    amount: number;
    status: string;
    userId: number; // This links the reimbursement to a specific user
}
