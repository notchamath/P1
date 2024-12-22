import { UserInterface } from './UserInterface';

export interface ReimbursementInterface {
    reimbId?: number;
    description: string;
    amount: number;
    status: string;
    user: UserInterface; // This links the reimbursement to a specific user
}
